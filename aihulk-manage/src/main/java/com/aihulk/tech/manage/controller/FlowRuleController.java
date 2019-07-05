package com.aihulk.tech.manage.controller;

import com.aihulk.tech.entity.entity.FlowRule;
import com.aihulk.tech.manage.service.FlowRuleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyibo
 * @title: FlowRuleController
 * @projectName aihulk
 * @description: FlowRuleController
 * @date 2019-07-0414:15
 */
@RestController
@RequestMapping(value = "/flowRule")
public class FlowRuleController extends BaseController<FlowRule, FlowRuleService> {
}
