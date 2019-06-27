package com.aihulk.tech.manage.controller;

import com.aihulk.tech.common.entity.Fact;
import com.aihulk.tech.manage.service.FactService;
import com.aihulk.tech.manage.vo.BaseResponseVo;
import com.aihulk.tech.manage.vo.ResponsePageVo;
import com.aihulk.tech.manage.vo.ResponseVo;
import com.github.pagehelper.PageHelper;

import static com.google.common.base.Preconditions.*;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "")
    public BaseResponseVo<List<Fact>> select(@RequestParam(required = false) Integer start,
                                             @RequestParam(required = false) Integer pageSize,
                                             Fact fact) {
        boolean pageQuery = false;
        if (start != null && start > 0 && pageSize != null && pageSize > 0) {
            PageHelper.startPage(start, pageSize);
            pageQuery = true;
        }
        List<Fact> facts = factService.select(fact);
        if (pageQuery) {
            return new ResponsePageVo<Fact>().buildSuccess(facts, "ok", start, pageSize);
        } else {
            return new ResponseVo<List<Fact>>().buildSuccess(facts, "ok");
        }
    }

    @PostMapping(value = "")
    public ResponseVo<Void> add(@RequestBody Fact fact) {
        checkArgument(!Strings.isNullOrEmpty(fact.getName()), "fact.name 不能为空");
        checkArgument(!Strings.isNullOrEmpty(fact.getNameEn()), "fact.nameEn 不能为空");
        checkArgument(!Strings.isNullOrEmpty(fact.getCode()), "fact.code 不能为空");
        checkArgument(!Strings.isNullOrEmpty(fact.getCodeType()), "fact.codeType 不能为空");
        checkArgument(!Strings.isNullOrEmpty(fact.getResultType()), "fact.resultType 不能为空");
        checkNotNull(fact.getBusinessId(), "fact.businessId 不能为空");
        //TODO 操作人和更新时间
        factService.add(fact);
        return new ResponseVo<Void>().buildSuccess("添加成功");
    }

    @PutMapping(value = "")
    public ResponseVo<Void> update(@RequestBody Fact fact) {
        checkNotNull(fact.getId(), "fact.id不能为空");
        checkArgument(fact.getId() > 0, "fact.id参数不合法");
        factService.update(fact);
        return new ResponseVo<Void>().buildSuccess("修改成功");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseVo<Void> delete(@PathVariable(value = "id") Integer id) {
        checkNotNull(id, "id不能为空");
        checkArgument(id > 0, "id参数不合法");
        factService.delete(id);
        return new ResponseVo<Void>().buildSuccess("删除成功");
    }

}
