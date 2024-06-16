import cn.hit.sw.entity.MyGraph;
import cn.hit.sw.lab1.impl.GeneratorImpl;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WhiteBoxTest {

    @Test
    public void testUnreachableNode() {
        MyGraph graph = new MyGraph("testUnreachableNode");
        graph.addNode("a").setAttribute("ui.label", "a");
        graph.addNode("b").setAttribute("ui.label", "b");
        graph.addNode("c").setAttribute("ui.label", "c");
        graph.addNode("d").setAttribute("ui.label", "d");
        graph.addEdge("a-b", "a", "b", true).setAttribute("ui.label", 1);
        graph.addEdge("a-d", "a", "d", true).setAttribute("ui.label", 1);
        graph.addEdge("b-c", "b", "c", true).setAttribute("ui.label", 1);

        String result = new GeneratorImpl(graph).findShortestPath(graph, "b", "d");

        assertEquals("不可达", result);
        System.out.println("SUCCESS");
        graph.myDisplay(graph);

        try {
            sleep(10000);
        } catch (InterruptedException e) {}
    }

    @Test
    public void testReachableNode() {
        MyGraph graph = new MyGraph("testReachableNode");
        graph.addNode("a").setAttribute("ui.label", "a");
        graph.addNode("b").setAttribute("ui.label", "b");
        graph.addNode("c").setAttribute("ui.label", "c");
        graph.addNode("d").setAttribute("ui.label", "d");
        graph.addEdge("a-b", "a", "b", true).setAttribute("ui.label", 1);
        graph.addEdge("a-d", "a", "d", true).setAttribute("ui.label", 1);
        graph.addEdge("b-c", "b", "c", true).setAttribute("ui.label", 1);
        graph.addEdge("c-a", "c", "a", true).setAttribute("ui.label", 1);

        String result = new GeneratorImpl(graph).findShortestPath(graph, "b", "d");

        assertEquals("Path total weight: 3", result);
        System.out.println("SUCCESS");
        graph.myDisplay(graph);
        try {
            sleep(10000);
        } catch (InterruptedException e) {}

    }
}
