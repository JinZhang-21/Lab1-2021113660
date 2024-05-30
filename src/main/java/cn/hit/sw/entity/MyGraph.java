package cn.hit.sw.entity;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.view.Viewer;

import java.awt.*;

public class MyGraph extends SingleGraph {
    public MyGraph(String id, boolean strictChecking, boolean autoCreate, int initialNodeCapacity, int initialEdgeCapacity) {
        super(id, strictChecking, autoCreate, initialNodeCapacity, initialEdgeCapacity);
    }

    public MyGraph(String id, boolean strictChecking, boolean autoCreate) {
        super(id, strictChecking, autoCreate);
    }

    public MyGraph(String id) {
        super(id);
    }

    public void myDisplay(Graph graph) {
        graph.setAttribute("ui.stylesheet",
                "node {" +
                        "   fill-color: black;" +
                        "   size: 20px;" +
                        "   text-alignment: above;" +
                        "   text-size: 20;" +
                        "   text-color: black;" +
                        "   text-style: bold;" +
                        "   text-alignment: under;" +
                        "}" +
                        "edge {" +
                        "   fill-color: black;" +
                        "   size: 3px;" +
                        "   text-size: 20;" +
                        "   text-color: blue;" +
                        "   text-style: bold;" +
                        "   text-alignment: along;" +
                        "   text-offset: 0px, 10px;" +
                        "   arrow-size: 20px, 10px;" +
                        "}");

        // 显示图形
        Viewer viewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        javax.swing.JFrame frame = new javax.swing.JFrame("GraphStream");
        frame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1200, 800));
        javax.swing.JPanel panel = (javax.swing.JPanel) viewer.addDefaultView(false);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
