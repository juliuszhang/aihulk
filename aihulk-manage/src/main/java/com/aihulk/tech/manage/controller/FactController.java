package com.aihulk.tech.manage.controller;

import com.aihulk.tech.common.entity.Fact;
import com.aihulk.tech.manage.service.FactService;
import com.aihulk.tech.manage.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangyibo
 * @title: FactController
 * @projectName aihulk
 * @description: FactController
 * @date 2019-06-2616:54
 */
@RestController
@RequestMapping(value = "/fact")
public class FactController {

    @Autowired
    private FactService factService;

    @GetMapping(value = "/all")
    public ResponseVo<List<Fact>> selectAll() {
        List<Fact> facts = factService.selectAll();
        return new ResponseVo<List<Fact>>().buildSuccess(facts, "ok");
    }

}
