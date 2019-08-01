
package com.aihulk.tech.decision.component.mvc.data.response;

import io.netty.handler.codec.http.FullHttpResponse;

public interface Response {
    void put(String name, Object data);
    
    FullHttpResponse response();
}
