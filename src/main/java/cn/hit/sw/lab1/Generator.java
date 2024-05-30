package cn.hit.sw.lab1;

import cn.hit.sw.entity.MyGraph;

public interface Generator {
    // 展示有向图
    void showDirectedGraph(MyGraph G);
    // 查询桥接词
    String queryBridgeWords(String word1, String word2);
    // 根据bridge word生成新文本
    String generateNewText(String inputText);
    // 计算两个单词之间的最短路径
    String calcShortestPath(String word1, String word2);
    // 随机游走
    void randomWalk();
}
