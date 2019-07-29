package com.aihulk.tech.core.component;

import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Auther: zhangyibo
 * @Date: 2019/2/22 17:12
 * @Description: 执行抽取特征的脚本的Engine
 */
public interface ScriptEngine {

    /**
     * 执行单个脚本
     *
     * @param scriptInfo
     * @return
     */
    Object execute(ScriptInfo scriptInfo);

    /**
     * 执行一组脚本
     *
     * @param scriptInfos
     * @return
     */
    Map<Integer, Object> execute(List<ScriptInfo> scriptInfos);

    /**
     * 当执行一组脚本时 需要将结果与对应的脚本对应起来
     * 因此需要将id和脚本代码一同作为参数传入
     */
    @Getter
    class ScriptInfo {
        /**
         * 脚本Id
         */
        private Integer scriptId;

        /**
         * 脚本代码
         */
        private String script;

        /**
         * 要执行的参数
         */
        private Map<String, Object> params;

        public ScriptInfo(Integer scriptId, String script) {
            this.scriptId = scriptId;
            this.script = script;
        }

        public ScriptInfo(Integer scriptId, String script, Map<String, Object> params) {
            this.scriptId = scriptId;
            this.script = script;
            this.params = params;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ScriptInfo that = (ScriptInfo) o;
            return Objects.equals(scriptId, that.scriptId) &&
                    Objects.equals(script, that.script);
        }

        @Override
        public int hashCode() {
            return Objects.hash(scriptId, script);
        }
    }

}

