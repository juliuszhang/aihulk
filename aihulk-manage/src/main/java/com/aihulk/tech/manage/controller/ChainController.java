package com.aihulk.tech.manage.controller;

import com.aihulk.tech.entity.entity.Chain;
import com.aihulk.tech.manage.service.ChainService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyibo
 * @title: ChainController
 * @projectName aihulk
 * @description: chain
 * @date 2019-07-0414:12
 */
@RestController
@RequestMapping(value = "/chain")
public class ChainController extends BaseController<Chain, ChainService> {
}
