package com.aihulk.tech.core.resource.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangyibo
 * @title: ExecuteUnitTest
 * @projectName aihulk
 * @description: ExecuteUnitTest
 * @date 2019-06-2015:04
 */
public class ExecuteUnitTest {
    DecisionBlock executeUnit = new DecisionBlock();

    @Before
    public void setUp() throws Exception {
        List<Fact> facts = Lists.newArrayList();
        Fact fact1 = new Fact();
        fact1.setId(1);
        Fact fact2 = new Fact();
        fact2.setId(2);
        Fact fact3 = new Fact();
        fact3.setId(3);
        Fact fact4 = new Fact();
        fact4.setId(4);
        Fact fact5 = new Fact();
        fact5.setId(5);
        Fact fact6 = new Fact();
        fact6.setId(6);
        Fact fact7 = new Fact();
        fact7.setId(7);
        Fact fact8 = new Fact();
        fact8.setId(8);

        facts.add(fact2);
        facts.add(fact4);
        facts.add(fact6);
        facts.add(fact8);
        facts.add(fact1);
        facts.add(fact3);
        facts.add(fact5);
        facts.add(fact7);

        Map<Integer, List<Fact>> relation = Maps.newHashMap();
        relation.put(1, Arrays.asList(fact5));
        relation.put(5, Arrays.asList(fact3));
        relation.put(3, Arrays.asList(fact8));
        relation.put(2, Arrays.asList(fact7));
        relation.put(7, Arrays.asList(fact4, fact5));
        relation.put(4, Arrays.asList(fact6));


//      1->5->3->8
//         |
//      2->7->4-6


        executeUnit.setFactsWithSort(facts, relation);

    }


    @Test
    public void sortFacts() {
        List<Fact> result = executeUnit.getFacts();
        System.out.println(result.stream().map(Fact::getId).collect(Collectors.toList()));
    }
}
