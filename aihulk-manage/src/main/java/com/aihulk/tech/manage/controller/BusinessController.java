package com.aihulk.tech.manage.controller;

import com.aihulk.tech.common.entity.Business;
import com.aihulk.tech.manage.service.BusinessService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyibo
 * @title: BusinessController
 * @projectName aihulk
 * @description: business
 * @date 2019-07-0414:11
 */
@RestController
@RequestMapping(value = "/business")
public class BusinessController extends BaseController<Business, BusinessService> {
}
