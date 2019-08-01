package com.aihulk.tech.decision.component.mvc;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public interface Constants {
    String FAVICON_ICO = "/favicon.ico";
    
    String CONNECTION_CLOSE = "close";
    
    String CONNECTION_KEEP_ALIVE = "keep-alive";
    
    String CONTENT_TYPE = "Content-Type";
    
    String JSON = "application/json";
    
    String FORM = "application/x-www-form-urlencoded";
    
    DefaultFullHttpResponse NOT_FOUND_RESPONSE = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
            HttpResponseStatus.NOT_FOUND,
            Unpooled.copiedBuffer("Not found this url.", CharsetUtil.UTF_8));
}
