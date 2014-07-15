package alda.graph;

import java.util.*;

class Edge<T> {
	private T dest;
	private int cost;

	public Edge(T dest, int cost){
		this.dest=dest;
		this.cost=cost;
	}

	public int getCost(){
		return cost;
	}

	public T getDest(){
		return dest;
	}

}


public class MyUndirectedGraph<T> implements UndirectedGraph<T> {

	private Map<T, List<Edge<T>>> nodes = new HashMap<T, List<Edge <T>>>();
	Edge<T> tmpEdge;

	public boolean add(T newNode) {

		if (nodes.containsKey(newNode))
			return false;

		nodes.put(newNode, new LinkedList<Edge<T>>());
		return true;
	}

	public boolean connect(T node1, T node2, int cost) {

		Edge<T> tmpEdge1 = new Edge<T>(node1, cost); //Edge1
		Edge<T> tmpEdge2 = new Edge<T>(node2, cost); //Edge2
		List<Edge<T>> tmpList1 = (nodes.get(node1)); //Listan för node1
		List<Edge<T>> tmpList2 = (nodes.get(node2)); //Listan för node2

		if ((!nodes.containsKey(node1)) || (!nodes.containsKey(node2)))
			return false;


		for (int i = 0; i<tmpList1.size() -1; i++){

			Edge<T> tmpEdge = tmpList1.get(i);

			if (node2.equals(tmpEdge.getDest())) {

				int index1 = tmpList1.indexOf(tmpEdge);
				int index2 = tmpList2.indexOf(tmpEdge2);

				tmpList1.set(index1, tmpEdge2);
				tmpList2.set(index2, tmpEdge1);

				return true;
			}	

		}

		tmpList1.add(new Edge<T>(node2, cost));
		tmpList2.add(new Edge<T>(node1, cost));
		return true;

	}

	public int getCost(T node1, T node2) {
		int cost = -1;

		if ((!nodes.containsKey(node1)) || (!nodes.containsKey(node2)))
			return cost;

		List<Edge<T>> tmpList;
		tmpList = nodes.get(node1);
		int index = (tmpList.indexOf(node2) +1);
		Edge<T> tmpEdge = tmpList.get(index);
		cost = tmpEdge.getCost();

		return cost;
	}

	public UndirectedGraph<T> minimumSpanningTree() {

		return null;
	}

	public Edge<T> getEdge(){
		return tmpEdge;
	}

}


