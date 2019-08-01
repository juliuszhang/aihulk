
package com.aihulk.tech.decision.component.mvc.core;

import com.aihulk.tech.common.util.JsonUtil;
import com.aihulk.tech.decision.component.mvc.Constants;
import com.aihulk.tech.decision.component.mvc.annotation.RequestMethod;
import com.aihulk.tech.decision.component.mvc.data.FormParam;
import com.aihulk.tech.decision.component.mvc.data.QueryParam;
import com.aihulk.tech.decision.component.mvc.data.RequestParam;
import com.aihulk.tech.decision.component.mvc.data.response.Response;
import com.aihulk.tech.decision.component.mvc.exception.InvalidRequestException;
import com.aihulk.tech.decision.component.mvc.exception.InvalidResponseException;
import com.fasterxml.jackson.core.type.TypeReference;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
/**
 * @author zhangyibo
 * @version 1.0
 * @ClassName:Action
 * @Description: 请求转发
 * @date 2019/8/1
 */
@ChannelHandler.Sharable
public class NettyRequestDispatcher extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyRequestDispatcher.class);
    
    private final RoutingContext routingContext = RoutingContext.getRoutingContext();
    
    // decode our post requests
    private HttpPostRequestDecoder decoder;
    private static final HttpDataFactory FACTORY = new DefaultHttpDataFactory(DefaultHttpDataFactory.MAXSIZE);
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // check routing init status
        if (!routingContext.isInitialized()) {
            routingContext.init();
        }
        // parse our request and send the mapped resource
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            String uri = request.uri();
            
            if (uri.equalsIgnoreCase(Constants.FAVICON_ICO)) {
                return; // discard the invalid request
            }
            
            try {
                doDispatch(request, uri, ctx);
            } catch (Throwable e) {
                LOGGER.error("Error occurs:", e);
                throw e;
            } finally {
                // avoid OOM
                ReferenceCountUtil.release(msg);
            }
        } else {
            // discard this request directly.
            ReferenceCountUtil.release(msg);
        }
    }
    
    /**
     * process request:
     * 1.parse uri
     * 2.build the params
     * 3.load the action method from actionMap -- move to doXXX method
     * 4.invoke the action method and build our response
     */
    private void doDispatch(HttpRequest request, String uri, ChannelHandlerContext ctx) throws Exception {
        HttpMethod requestMethod = request.method();
        RequestParam params = new RequestParam();
        FullHttpResponse response;
        uri = processQueryParams(uri, params);
        if (requestMethod.equals(HttpMethod.GET)) {
            // search for mapped resource,send response to client
            response = doGet(uri, params);
        } else if (requestMethod.equals(HttpMethod.POST)) {
            // we need to cast this object for latter processing.
            response = doPost(request, uri, params);
        } else {
            throw new InvalidRequestException();
        }
        // write response
        if (response != null) {
            ChannelFuture future = ctx.channel().write(response);
            if (!isShortConnection(request)) {
                future.addListener(ChannelFutureListener.CLOSE);
            }
        }
    }
    
    private FullHttpResponse doGet(String uri, RequestParam params) {
        ActionHandler handler = routingContext.getActionHandler(uri, RequestMethod.GET);
        return getResponse(params, handler);
    }
    
    private FullHttpResponse getResponse(RequestParam params, ActionHandler handler) {
        if (handler != null) {
            Object returnResult = ClassTracker.invokeMethod(routingContext.getSingletons().get(handler.getRouter()),
                    handler.getMethod(), params);
            if (returnResult instanceof Response) {
                return ((Response) returnResult).response();
            } else {
                throw new InvalidResponseException();
            }
        } else {
            return Constants.NOT_FOUND_RESPONSE;
        }
    }
    
    private String processQueryParams(String uri, RequestParam params) {
        QueryStringDecoder queryDecoder = new QueryStringDecoder(uri, CharsetUtil.UTF_8);
        Map<String, List<String>> uriAttributes = queryDecoder.parameters();
        if (uri.contains("?")) {
            uri = uri.substring(0, uri.indexOf("?"));
        }
        LOGGER.info(String.format("Processing request for path: %s", uri));
        // just process url query params
        for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
            List<String> attrValue = attr.getValue();
            if (attrValue != null) {
                if (attrValue.size() == 1) {
                    params.add(new QueryParam(attr.getKey(), attrValue.get(0)));
                } else {
                    params.add(new QueryParam(attr.getKey(), attrValue));
                }
            }
        }
        return uri;
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        FullHttpResponse exceptionResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.INTERNAL_SERVER_ERROR,
                Unpooled.copiedBuffer(cause.getMessage(), CharsetUtil.UTF_8));
        ctx.channel().writeAndFlush(exceptionResponse);
        ctx.close();
    }
    
    private FullHttpResponse doPost(HttpRequest request, String uri, RequestParam params) throws IOException {
        ActionHandler handler = this.routingContext.getActionHandler(uri, RequestMethod.POST);
        switch (getRequestContentType(request)) {
            // process different type of params.
            case Constants.JSON:
                // cast here for content processing.
                String content = ((FullHttpRequest) request).content().toString(CharsetUtil.UTF_8);
                Map<String, Object> map = JsonUtil.parseObject(content, new TypeReference<Map<String, Object>>() {
                });
                if (map != null) {
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        params.add(new FormParam(entry.getKey(), entry.getValue()));
                    }
                }
                break;
            case Constants.FORM:
                resetDecoder(request);
                for (InterfaceHttpData data : this.decoder.getBodyHttpDatas()) {
                    if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                        Attribute attribute = (Attribute) data;
                        params.add(new FormParam(attribute.getName(), attribute.getValue()));
                    }
                }
                break;
            default:
                throw new InvalidRequestException();
        }
        return getResponse(params, handler);
    }
    
    private void resetDecoder(HttpRequest request) {
        if (decoder != null) {
            decoder.cleanFiles();
            decoder = null;
        }
        decoder = new HttpPostRequestDecoder(FACTORY, request, CharsetUtil.UTF_8);
    }
    
    private String getRequestContentType(HttpRequest request) {
        // refer to https://stackoverflow.com/questions/3508338/what-is-the-boundary-in-multipart-form-data
        String contentType = request.headers().get(Constants.CONTENT_TYPE).split(":")[0];
        if (contentType.contains(";")) {
            return contentType.substring(0, contentType.indexOf(";"));
        }
        return contentType;
    }
    
    private boolean isShortConnection(HttpRequest request) {
        HttpHeaders headers = request.headers();
        return headers.contains(HttpHeaderNames.CONNECTION, Constants.CONNECTION_CLOSE, true) ||
                (request.protocolVersion().equals(HttpVersion.HTTP_1_0) &&
                        !headers.contains(HttpHeaderNames.CONNECTION, Constants.CONNECTION_KEEP_ALIVE, true));
    }
}
