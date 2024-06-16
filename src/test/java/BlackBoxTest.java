import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cn.hit.sw.entity.MyGraph;
import cn.hit.sw.lab1.impl.GeneratorImpl;
// import org.junit.Test;


// import static org.junit.Assert.assertEquals;


public class BlackBoxTest {
    @Test
    public void testValidBridgeWords() {
        MyGraph graph = new MyGraph("testValidBridgeWords");
        graph.addNode("a");
        graph.addNode("b");
        graph.addNode("c");
        graph.addEdge("a-b", "a", "b", true);
        graph.addEdge("b-c", "b", "c", true);

        String result = new GeneratorImpl(graph).queryBridgeWords("a", "c");
        assertEquals("The bridge words from a to c are:b.", result);
        System.out.println("SUCCESS");
    }

    @Test
    public void testNoBridgeWords() {
        MyGraph graph = new MyGraph("testNoBridgeWords");
        graph.addNode("a");
        graph.addNode("b");
        graph.addNode("d");
        graph.addNode("e");
        graph.addEdge("a-b", "a", "b", true);
        graph.addEdge("b-d", "b", "d", true);
        graph.addEdge("d-e", "d", "e", true);

        String result = new GeneratorImpl(graph).queryBridgeWords("a", "e");
        assertEquals("No bridge words from a to e!", result);
        System.out.println("SUCCESS");
    }

    @Test
    public void testMultipleBridgeWords() {
        MyGraph graph = new MyGraph("testMultipleBridgeWords");
        graph.addNode("a");
        graph.addNode("b");
        graph.addNode("d");
        graph.addNode("c");
        graph.addEdge("a-b", "a", "b", true);
        graph.addEdge("a-d", "a", "d", true);
        graph.addEdge("b-c", "b", "c", true);
        graph.addEdge("d-c", "d", "c", true);

        String result = new GeneratorImpl(graph).queryBridgeWords("a", "c");
        assertEquals("The bridge words from a to c are:b, and d.", result);
        System.out.println("SUCCESS");
    }

    @Test
    public void testWord1EqualsWord2() {
        MyGraph graph = new MyGraph("testWord1EqualsWord2");
        graph.addNode("a");
        graph.addNode("b");
        graph.addEdge("a-b", "a", "b", true);

        String result = new GeneratorImpl(graph).queryBridgeWords("a", "a");
        assertEquals("No bridge words from a to a!", result);
        System.out.println("SUCCESS");
    }

    @Test
    public void testWord1NotFound() {
        MyGraph graph = new MyGraph("testWord1NotFound");
        graph.addNode("b");
        graph.addNode("c");
        graph.addEdge("b-c", "b", "c", true);

        String result = new GeneratorImpl(graph).queryBridgeWords("a", "c");
        assertEquals("No word1 or word2 in the graph!", result);
        System.out.println("SUCCESS");
    }

    @Test
    public void testWord2NotFound() {
        MyGraph graph = new MyGraph("testWord2NotFound");
        graph.addNode("a");
        graph.addNode("b");
        graph.addEdge("a-b", "a", "b", true);

        String result = new GeneratorImpl(graph).queryBridgeWords("a", "d");
        assertEquals("No word1 or word2 in the graph!", result);
        System.out.println("SUCCESS");
    }

    @Test
    public void testWord1AndWord2NotFound() {
        MyGraph graph = new MyGraph("testWord1AndWord2NotFound");
        graph.addNode("b");
        graph.addNode("c");
        graph.addEdge("b-c", "b", "c", true);

        String result = new GeneratorImpl(graph).queryBridgeWords("x", "y");
        assertEquals("No word1 or word2 in the graph!", result);
        System.out.println("SUCCESS");
    }
}
