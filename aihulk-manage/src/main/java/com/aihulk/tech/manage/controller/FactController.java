package com.aihulk.tech.manage.controller;

import com.aihulk.tech.common.constant.ScriptCodeType;
import com.aihulk.tech.entity.entity.Fact;
import com.aihulk.tech.manage.controller.base.BaseControllerAdaptor;
import com.aihulk.tech.manage.service.FactService;
import com.aihulk.tech.manage.vo.ExecFactVo;
import com.aihulk.tech.manage.vo.base.ResponseVo;
import com.google.common.base.Strings;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author zhangyibo
 * @title: FactController
 * @projectName aihulk
 * @description: FactController
 * @date 2019-06-2616:54
 */
@Slf4j
@RestController
@RequestMapping(value = "/fact")
public class FactController extends BaseControllerAdaptor<Fact, FactService> {


    @Autowired
    private FactService factService;

    @Override
    public ResponseVo<Void> add(@RequestBody @NonNull Fact fact) {
        checkArgument(!Strings.isNullOrEmpty(fact.getName()), "name 不能为空");
        checkArgument(!Strings.isNullOrEmpty(fact.getNameEn()), "nameEn 不能为空");
//        checkArgument(!Strings.isNullOrEmpty(fact.getCode()), "fact.code 不能为空");
        String codeType = fact.getScriptType();
        checkArgument(!Strings.isNullOrEmpty(codeType), "codeType 不能为空");
        checkArgument(!Strings.isNullOrEmpty(fact.getResultType()), "resultType 不能为空");
//        checkNotNull(fact.getBusinessId(), "fact.businessId 不能为空");
        ScriptCodeType scriptCodeType = ScriptCodeType.parse(codeType);
        String formatScript = factService.formatScript(scriptCodeType, fact.getId(), fact.getScript());
        fact.setFormatScript(formatScript);
        return super.add(fact);
    }

    @PostMapping(value = "/exec")
    public ResponseVo<Object> exec(@RequestBody @NonNull ExecFactVo execFactVo) {
        String script = execFactVo.getScript();
        checkArgument(!Strings.isNullOrEmpty(script), "script不能为空");
        String scriptType = execFactVo.getScriptType();
        checkArgument(!Strings.isNullOrEmpty(scriptType), "scriptType 不能为空");
        ScriptCodeType scriptCodeType = ScriptCodeType.parse(scriptType);
        Object result = factService.exec(scriptCodeType, 1, script, execFactVo.getData(), false);
        return new ResponseVo<>().buildSuccess(result, "执行成功");
    }

}
