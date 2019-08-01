
package com.aihulk.tech.decision.component.mvc.data.response;

import com.aihulk.tech.common.util.JsonUtil;
import com.aihulk.tech.decision.component.mvc.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class JsonResponse extends AbstractResponse {

    @Override
    public FullHttpResponse response() {
        ByteBuf content = this.content();
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
        response.headers().add(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
        response.headers().add(HttpHeaderNames.CONTENT_TYPE, Constants.JSON);
        return response;
    }

    @Override
    protected ByteBuf content() {
        if (this.paramMap != null && !this.paramMap.isEmpty()) {
            ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer();
            byteBuf.writeCharSequence(JsonUtil.toJsonString(paramMap), CharsetUtil.UTF_8);
            return byteBuf;
        } else {
            return Unpooled.EMPTY_BUFFER;
        }
    }

}
