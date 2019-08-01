package com.aihulk.tech.decision.component.mvc.core;

import com.aihulk.tech.decision.component.mvc.annotation.RequestMethod;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


/**
 * @author zhangyibo
 * @version 1.0
 * @ClassName:RoutingRequest
 * @Description:
 * @date 2019/8/1
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class RoutingRequest {

    private final String path;

    private final RequestMethod[] requestMethods;
}
