package com.aihulk.tech.manage.controller;

import com.aihulk.tech.core.component.JsScriptEngine;
import com.aihulk.tech.core.component.ScriptEngine;
import com.aihulk.tech.core.component.SimpleScriptEngine;
import com.aihulk.tech.entity.entity.Fact;
import com.aihulk.tech.manage.controller.base.BaseControllerAdaptor;
import com.aihulk.tech.manage.exception.ManageException;
import com.aihulk.tech.manage.service.FactService;
import com.aihulk.tech.manage.vo.ExecFactVo;
import com.aihulk.tech.manage.vo.base.BaseResponseVo;
import com.aihulk.tech.manage.vo.base.ResponseVo;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @Override
    public ResponseVo<Void> add(@RequestBody @NonNull Fact fact) {
        checkArgument(!Strings.isNullOrEmpty(fact.getName()), "name 不能为空");
        checkArgument(!Strings.isNullOrEmpty(fact.getNameEn()), "nameEn 不能为空");
//        checkArgument(!Strings.isNullOrEmpty(fact.getCode()), "fact.code 不能为空");
        checkArgument(!Strings.isNullOrEmpty(fact.getCodeType()), "codeType 不能为空");
        checkArgument(!Strings.isNullOrEmpty(fact.getResultType()), "resultType 不能为空");
//        checkNotNull(fact.getBusinessId(), "fact.businessId 不能为空");
        return super.add(fact);
    }

    @PostMapping(value = "/exec")
    public ResponseVo<Object> exec(@RequestBody @NonNull ExecFactVo execFactVo) {
        String code = execFactVo.getCode();
        checkArgument(!Strings.isNullOrEmpty(code), "code不能为空");
        String codeType = execFactVo.getCodeType();
        checkArgument(!Strings.isNullOrEmpty(codeType), "codeType 不能为空");
        ScriptEngine scriptEngine;
        if (Fact.CODE_TYPE_BASIC.equalsIgnoreCase(codeType)) {
            scriptEngine = new SimpleScriptEngine();
        } else if (Fact.CODE_TYPE_JS.equalsIgnoreCase(codeType)) {
            scriptEngine = new JsScriptEngine();
        } else if (Fact.CODE_TYPE_PY.equalsIgnoreCase(codeType)) {
            throw new UnsupportedOperationException();
            //TODO
        } else {
            log.error("can not find script engine for this code type. code type name = {}", codeType);
            throw new ManageException(BaseResponseVo.ManageBusinessErrorCode.UNKNOWN_SCRIPT_ENGING, "找不到合适的脚本引擎，code type=" + execFactVo.getCodeType());
        }
        Map<String, Object> paramMap = Maps.newHashMapWithExpectedSize(1);
        paramMap.put("data", execFactVo.getData());
        Object result = scriptEngine.execute(new ScriptEngine.ScriptInfo(execFactVo.getFactId(), execFactVo.getCode(), paramMap));
        return new ResponseVo<>().buildSuccess(result, "执行成功");
    }

}
