package com.aihulk.tech.manage.controller;

import com.aihulk.tech.entity.entity.Variable;
import com.aihulk.tech.manage.service.VariableService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyibo
 * @title: VariableController
 * @projectName aihulk
 * @description: 变量
 * @date 2019-07-0316:01
 */
@RestController
@RequestMapping(value = "/variable")
public class VariableController extends BaseController<Variable, VariableService> {

}
