package com.aihulk.tech.manage.controller;

import com.aihulk.tech.entity.entity.UnitGroup;
import com.aihulk.tech.manage.controller.base.BaseControllerAdaptor;
import com.aihulk.tech.manage.service.UnitGroupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyibo
 * @title: UnitGroupController
 * @projectName aihulk
 * @description: unitGroup
 * @date 2019-07-0414:14
 */
@RestController
@RequestMapping(value = "/unitGroup")
public class UnitGroupController extends BaseControllerAdaptor<UnitGroup, UnitGroupService> {
}
