package com.aihulk.tech.manage.controller;

import com.aihulk.tech.common.entity.Unit;
import com.aihulk.tech.manage.service.UnitService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyibo
 * @title: UnitController
 * @projectName aihulk
 * @description: unit
 * @date 2019-07-0414:13
 */
@RestController
@RequestMapping(value = "/unit")
public class UnitController extends BaseController<Unit, UnitService> {
}
