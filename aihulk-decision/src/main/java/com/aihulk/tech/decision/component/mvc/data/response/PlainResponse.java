
package com.aihulk.tech.decision.component.mvc.data.response;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpResponse;

public class PlainResponse extends AbstractResponse {
    @Override
    public FullHttpResponse response() {
        return null;
    }
    
    @Override
    protected ByteBuf content() {
        return null;
    }
}
