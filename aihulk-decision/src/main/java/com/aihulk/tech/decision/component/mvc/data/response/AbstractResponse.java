
package com.aihulk.tech.decision.component.mvc.data.response;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

import java.util.HashMap;
import java.util.Map;


abstract class AbstractResponse implements Response {
    protected Map<String, Object> paramMap = new HashMap<>();
    
    @Override
    public void put(String name, Object data) {
        this.paramMap.put(name, data);
    }
    
    protected abstract ByteBuf content();
    
    protected static final PooledByteBufAllocator ALLOCATOR = PooledByteBufAllocator.DEFAULT;
    
}
