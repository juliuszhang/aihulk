package com.aihulk.tech.resource.data;

import java.util.LinkedList;

/**
 * @author zhangyibo
 * @version 1.0
 * @ClassName:Graph
 * @Description: TODO
 * @date 2019/5/19
 */
public class Graph {

    private int v;

    private LinkedList<Integer> adj[];

    public Graph(int v) {
        this.v = v;
        this.adj = new LinkedList[v];
    }

    public void addEdge(int s, int t) {

    }
}
