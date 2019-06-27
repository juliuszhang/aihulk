package com.aihulk.tech.manage.controller;

import com.aihulk.tech.common.entity.Fact;
import com.aihulk.tech.manage.service.FactService;
import com.aihulk.tech.manage.vo.BaseResponseVO;
import com.aihulk.tech.manage.vo.ResponsePageVo;
import com.aihulk.tech.manage.vo.ResponseVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public BaseResponseVO<List<Fact>> selectAll(@RequestParam(name = "start", required = false) Integer start,
                                                @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        boolean pageQuery = false;
        if (start != null && start > 0 && pageSize != null && pageSize > 0) {
            PageHelper.startPage(start, pageSize);
            pageQuery = true;
        }
        List<Fact> facts = factService.selectAll();
        if (pageQuery) {
            return new ResponsePageVo<Fact>().buildSuccess(facts, "ok", start, pageSize);
        } else {
            return new ResponseVo<List<Fact>>().buildSuccess(facts, "ok");
        }
    }

}
