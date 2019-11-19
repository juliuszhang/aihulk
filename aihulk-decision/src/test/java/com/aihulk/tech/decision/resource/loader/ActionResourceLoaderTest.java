package com.aihulk.tech.decision.resource.loader;

import com.aihulk.tech.common.constant.MergeStrategy;
import com.aihulk.tech.core.action.Action;
import com.aihulk.tech.core.action.OutPut;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

/**
 * @author zhangyibo
 * @title: ActionResourceLoaderTest
 * @projectName aihulk
 * @description: ActionResourceLoaderTest
 * @date 2019-07-0516:07
 */

public class ActionResourceLoaderTest extends AbstractTest {

    @Autowired
    private ActionResourceLoader actionResourceLoader = new ActionResourceLoader();

    @Test
    public void loadResource() {
        Map<Integer, List<Action>> actionMap = actionResourceLoader.loadResource(1, "");
        List<Action> actions = actionMap.entrySet().stream().flatMap(entry -> entry.getValue().stream()).sorted(Comparator.comparing(Action::getId)).collect(Collectors.toList());
        Action action1 = actions.get(actions.size() - 2);
        Action action2 = actions.get(actions.size() - 1);

        assertTrue(action1 instanceof OutPut);
        assertTrue(action2 instanceof OutPut);

        OutPut outPut1 = (OutPut) action1;
        OutPut outPut2 = (OutPut) action2;

        assertTrue(outPut1.getKey().equals("test variable 1"));
        assertTrue(outPut2.getKey().equals("test variable 2"));

        assertTrue(outPut1.getObjExp().equals("1"));
        assertTrue(outPut2.getObjExp().equals("aaa"));
        assertTrue(outPut1.getUnitMergeStrategy() == MergeStrategy.ALL);
        assertTrue(outPut2.getUnitMergeStrategy() == MergeStrategy.OVERWRITE);
        assertTrue(outPut1.getChainMergeStrategy() == MergeStrategy.NOTOVERWRITE);
        assertTrue(outPut2.getChainMergeStrategy() == MergeStrategy.NOTOVERWRITE);
    }

}
