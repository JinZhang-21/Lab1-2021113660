package cn.hit.sw;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.layout.Layout;
import org.graphstream.ui.layout.Layouts;
import org.graphstream.ui.view.Viewer;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class bk {
    public static void main(String[] args) {
        
    }
    
    // Define the style sheet
    private static String styleSheet() {
        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = new SingleGraph("Example");

        graph.setAttribute("ui.stylesheet", styleSheet());
        graph.setAttribute("ui.antialias");
        graph.setAttribute("ui.quality");

        graph.addNode("A").setAttribute("ui.label", "A");
        graph.addNode("B").setAttribute("ui.label", "B");
        graph.addNode("C").setAttribute("ui.label", "C");
        Edge edge = graph.addEdge("A_B", "A", "B", true);
        Edge edge1 = graph.addEdge("B_C", "B", "C", true);
        Edge edge2 = graph.addEdge("C_A", "C", "A", true);
        Edge edge3 = graph.addEdge("B_A", "B", "A", true);
        edge.setAttribute("ui.label", 5);
        edge1.setAttribute("ui.label", 3.5);
        edge2.setAttribute("ui.label", 2);
        edge.setAttribute("ui.style", "text-offset: 0px, -5px;");
        edge3.setAttribute("ui.label", 1);
        edge3.setAttribute("ui.style", "text-offset: 0px, 30px;");
        // 使用自动布局
        Viewer viewer = graph.display();
        Layout layout = Layouts.newLayoutAlgorithm();
        viewer.enableAutoLayout(layout);
        return "node {" +
                "   fill-color: black;" +
                "   size: 20px;" +
                "   text-alignment: above;" +
                "   text-size: 20;" +
                "   text-color: red;" +
                "   text-style: bold;" +
                "   text-alignment: under;" +
                "}" +
                "edge {" +
                "   fill-color: grey;" +
                "   size: 3px;" +
                "   text-size: 20;" +
                "   text-color: blue;" +
                "   text-style: bold;" +
                "   text-alignment: along;" +
                "   text-offset: 0px, 10px;" +
                "}";
    }
}