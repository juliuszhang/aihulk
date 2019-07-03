package com.aihulk.tech.manage.controller;

import com.aihulk.tech.common.entity.Fact;
import com.aihulk.tech.manage.service.FactService;
import com.aihulk.tech.manage.vo.ResponseVo;
import com.google.common.base.Strings;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author zhangyibo
 * @title: FactController
 * @projectName aihulk
 * @description: FactController
 * @date 2019-06-2616:54
 */
@RestController
@RequestMapping(value = "/fact")
public class FactController extends BaseController<Fact, FactService> {

    @Override
    public ResponseVo<Void> add(@RequestBody Fact fact) {
        checkArgument(!Strings.isNullOrEmpty(fact.getName()), "fact.name 不能为空");
        checkArgument(!Strings.isNullOrEmpty(fact.getNameEn()), "fact.nameEn 不能为空");
        checkArgument(!Strings.isNullOrEmpty(fact.getCode()), "fact.code 不能为空");
        checkArgument(!Strings.isNullOrEmpty(fact.getCodeType()), "fact.codeType 不能为空");
        checkArgument(!Strings.isNullOrEmpty(fact.getResultType()), "fact.resultType 不能为空");
        checkNotNull(fact.getBusinessId(), "fact.businessId 不能为空");
        return super.add(fact);
    }

}
