package com.aihulk.tech.core;

import com.aihulk.tech.core.logic.Logic;
import com.aihulk.tech.core.logic.LogicHelper;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangyibo
 * @title: RegexTest
 * @projectName aihulk
 * @description: TODO
 * @date 2019-06-2110:50
 */
public class RegexTest {

    @Test
    public void test() {
        String regex = "\\$ref_fact_\\d{1,}";
        Pattern pattern = Pattern.compile(regex);
        String input = "function $fact_001(data){ return $ref_fact_002} \n $fact_001(data);";
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String group = matcher.group();

            System.out.println(group);
        }
    }

    @Test
    public void test2() {
        String logicStr = "{\"and\":[{\"src\":\"3\",\"op\":\"GT\",\"dest\":\"4\"},{\"or\":[{}]}]}";
        Logic parse = LogicHelper.parse(logicStr);
        System.out.println(parse);
    }

}
