package cn.hit.sw.lab1.impl;

import cn.hit.sw.entity.MyGraph;
import cn.hit.sw.lab1.Generator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;


import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class GeneratorImpl implements Generator {

    MyGraph graph;
    private Node current;
    private Set<Edge> visitedEdges;
    
    public GeneratorImpl(MyGraph graph) {
        this.graph = graph;
    }

    public MyGraph getGraph() {
        return this.graph;
    }
    
    @Override
    public void showDirectedGraph(MyGraph g) {
        for (Edge edge : graph.edges().toList()) {
            String source = edge.getSourceNode().getId();
            String target = edge.getTargetNode().getId();
            int weight = Integer.parseInt(edge.getAttribute("ui.label").toString());
            System.out.println(source + "->" + target + ", 权重: " + weight);
        }
        // 控制台打印
        this.graph.myDisplay(this.graph);
    }

    /** 输入的word1或word2如果不在图中出现，则输出“No word1 or word2 in the graph!”
      * 如果不存在桥接词，则输出“No bridge words from word1 to word2!”
      * 如果存在一个或多个桥接词，则输出“The bridge words from word1 to word2 are: xxx, xxx, and xxx.”
     **/
    @Override
    public String queryBridgeWords(String word1, String word2) {
        if(this.graph.getNode(word1) == null || this.graph.getNode(word2) == null){
            return "No word1 or word2 in the graph!";
        }
        List<String> stringList = queryBridgeWordsLists(word1, word2);

        if(stringList.isEmpty()) {
            return "No bridge words from " + word1 +" to "+word2 + "!\n";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The bridge words from ").append(word1).append(" to ").append(word2).append(" are:");

        if(stringList.size() == 1) {
            stringBuilder.append(stringList.get(0)).append(".");
        } else {
            for(int i = 0 ; i < stringList.size() ; i++){
                if(i == stringList.size() - 1) {
                    stringBuilder.append("and ").append(stringList.get(i)).append(".");
                    break;
                }
                stringBuilder.append(stringList.get(i)).append(", ");
            }
        }

        return stringBuilder.toString();
    }

    private List<String> queryBridgeWordsLists(String word1, String word2) {
        List<String> stringList = new LinkedList<>();
        if(this.graph.getNode(word1) == null || this.graph.getNode(word2) == null){
            return stringList;
        }
        Node node1 = this.graph.getNode(word1);
        Node node2 = this.graph.getNode(word2);
        node1.leavingEdges()
                .forEach(edge -> {
                    Node intermediate = edge.getTargetNode();
                    intermediate.leavingEdges()
                            .filter(e -> e.getTargetNode().equals(node2))
                            .forEach(e -> stringList.add(intermediate.getId()));
                });
        return stringList;
    }

    @Override
    public String generateNewText(String inputText) {
        Random random = new Random();
        String[] words = inputText.toLowerCase().split(" ");
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < (words.length - 1); i++) {
            List<String> bridgeWordsLists = this.queryBridgeWordsLists(words[i], words[i + 1]);

            if(!bridgeWordsLists.isEmpty()) {
                stringBuilder.append(words[i]).append(" ");
                stringBuilder.append(bridgeWordsLists.get(random.nextInt(bridgeWordsLists.size()))).append(" ");
            } else {
                stringBuilder.append(words[i]).append(" ");
            }
        }
        stringBuilder.append(words[words.length - 1]);
        return stringBuilder.toString().trim();
    }

    @Override
    public String calcShortestPath(String word1, String word2) {

        if (graph.getNode(word1) == null && graph.getNode(word2) == null) {
            return "One of the specified nodes does not exist.";
        } else if(this.graph.getNode(word1) == null || this.graph.getNode(word2) == null) {
            StringBuilder resultStr = new StringBuilder();
            String word = (word1 != null ? word1 : word2);
            List<Node> nodeList = this.graph.nodes().toList();
            for(Node node : nodeList) {
                String nodeId = node.getId();
                if(!nodeId.equals(word)) {
                    resultStr.append("to Node \"").append(nodeId).append("\"").append(findShortestPath(this.graph, word, nodeId)).append("\n");
                }
            }
            return resultStr.toString();
        }
        return findShortestPath(this.graph, word1, word2);
    }
    private String findShortestPath(MyGraph graph, String startWord, String endWord) {
        Map<Node, Integer> distances = new HashMap<>();
        Map<Node, Node> predecessors = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        // 初始化距离和前驱
        for (Node node : graph) {
            distances.put(node, Integer.MAX_VALUE);
            predecessors.put(node, null);
            node.setAttribute("ui.style", "fill-color: black;");
        }

        Node startNode = graph.getNode(startWord);
        distances.put(startNode, 0);
        queue.add(startNode);

        // 主循环
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.getId().equals(endWord)) {
                break;
            }

            for (Edge edge : current.leavingEdges().toList()) {
                Node adjacent = edge.getOpposite(current);
                int weight = Integer.parseInt(edge.getAttribute("ui.label").toString());
                int distanceThroughCurrent = distances.get(current) + weight;

                if (distanceThroughCurrent < distances.get(adjacent)) {
                    distances.put(adjacent, distanceThroughCurrent);
                    predecessors.put(adjacent, current);
                    queue.add(adjacent);
                }
            }
        }

        // 检查终点是否可达
        if (distances.get(graph.getNode(endWord)) == Integer.MAX_VALUE) {
            return "不可达";
        }

        // 回溯找到路径
        List<Node> path = new ArrayList<>();
        Node step = graph.getNode(endWord);
        while (predecessors.get(step) != null) {
            path.add(step);
            step = predecessors.get(step);
        }
        path.add(graph.getNode(startWord));
        Collections.reverse(path);

        // 设置路径上的边为红色
        Node tmp = graph.getNode(endWord);
        while (predecessors.get(tmp) != null) {
            Node prev = predecessors.get(tmp);
            Edge edge = prev.getEdgeBetween(tmp);
            if (edge != null) {
                edge.setAttribute("ui.style", "fill-color: red;");
            }
            tmp = prev;
        }

        return "Path total weight: " + distances.get(graph.getNode(endWord));
    }

    @Override
    public void randomWalk() {
        Random random = new Random();

        current = graph.getNode(random.nextInt(graph.getNodeCount()));
        visitedEdges = new HashSet<>();
        StringBuilder path = new StringBuilder();

        // 创建 JFrame 实例
        JFrame frame = new JFrame("随机游走");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        String foutput = JOptionPane.showInputDialog(frame, "请输入保存文件路径:");
        if(foutput == null) {
            return;
        }

        // 创建按钮
        JButton nextButton = new JButton("继续游走");
        JButton exitButton = new JButton("停止");

        // 创建文本标签
        JLabel textLabel = new JLabel("路径: " + path);
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // 添加按钮的动作监听器
        nextButton.addActionListener(e -> {
            try (FileWriter writer = new FileWriter(foutput, true)) {

                if(current != null && current.getOutDegree() > 0) {
                    path.append(current.getId()).append(" -> ");
                    writer.write(current.getId() + " ");

                    // Randomly select an edge
                    int index = random.nextInt(current.getOutDegree());
                    Edge edge = current.getLeavingEdge(index);

                    if (!visitedEdges.add(edge)) {
                        JOptionPane.showMessageDialog(frame, "到达已访问的边,停止随机游走");
                        frame.dispose();
                    }

                    current = edge.getOpposite(current);
                    textLabel.setText("Path: " + path);
                }

                if (current != null && current.getDegree() == 0) {
                    JOptionPane.showMessageDialog(frame, "该节点没有出边,结束游走");
                    path.append(current.getId());
                    writer.write(current.getId() + "\n");
                    frame.dispose();
                }
                // 更新文本值并重新设置标签
                textLabel.setText("原始文本: " + path);
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(frame, exception.getMessage());
            }
        });

        exitButton.addActionListener(e -> {
            // 关闭窗口和程序
            frame.dispose();
        });

        // 添加组件到窗口
        frame.getContentPane().add(textLabel);
        frame.getContentPane().add(nextButton);
        frame.getContentPane().add(exitButton);
        // 设置窗口可见
        frame.setVisible(true);

    }
}