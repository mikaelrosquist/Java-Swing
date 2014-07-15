package alda.graph;

import static org.junit.Assert.*;

import org.junit.*;

public class UndirectedGraphTest {

	UndirectedGraph<String> graph = new MyUndirectedGraph();

	private void add(String... nodes) {
		for (String node : nodes) {
			assertTrue(graph.add(node));
		}
	}

	private void connect(String node1, String node2, int cost) {
		assertTrue(graph.connect(node1, node2, cost));
		assertEquals(cost, graph.getCost(node1, node2));
		assertEquals(cost, graph.getCost(node2, node1));
	}

	private void addExampleNodes() {
		add("A", "B", "C", "D", "E", "F", "G", "H", "I", "J");
	}

	private void createExampleGraph() {
		addExampleNodes();

		connect("A", "A", 1);
		connect("A", "G", 3);
		connect("G", "B", 28);
		connect("B", "F", 5);
		connect("F", "F", 3);
		connect("F", "H", 1);
		connect("H", "D", 1);
		connect("H", "I", 3);
		connect("D", "I", 1);
		connect("B", "D", 2);
		connect("B", "C", 3);
		connect("C", "D", 5);
		connect("E", "C", 2);
		connect("E", "D", 2);
		connect("J", "D", 5);
	}

	@Test
	public void testAdd() {
		addExampleNodes();
		assertFalse(graph.add("D"));
		assertFalse(graph.add("J"));
		assertTrue(graph.add("K"));
	}

	@Test
	public void testConnect() {
		addExampleNodes();
		assertFalse(graph.connect("A", "Z", 5));
		assertEquals(-1, graph.getCost("A", "Z"));
		assertFalse(graph.connect("X", "B", 5));
		assertEquals(-1, graph.getCost("X", "B"));
		assertEquals(-1, graph.getCost("B", "X"));

		assertTrue(graph.connect("A", "G", 5));
		assertEquals(5, graph.getCost("A", "G"));
		assertEquals(5, graph.getCost("G", "A"));
		assertTrue(graph.connect("G", "A", 3));
		assertEquals(3, graph.getCost("A", "G"));
		assertEquals(3, graph.getCost("G", "A"));
	}

	@Test
	public void testExampleGraph() {
		createExampleGraph();
	}

	@Test
	public void testMinimumSpanningTree() {
		createExampleGraph();
		UndirectedGraph<String> mst = graph.minimumSpanningTree();

		int totalEdges = 0;
		int totalCost = 0;

		for (char node1 = 'A'; node1 <= 'J'; node1++) {
			for (char node2=node1; node2 <= 'J'; node2++) {
				int cost = mst.getCost("" + node1, "" + node2);
				if (cost > -1) {
					totalEdges++;
					totalCost += cost;
				}
			}
		}

		assertEquals(9, totalEdges);
		assertEquals(45, totalCost);
	}

}

