package graphs;

import java.util.*;

public class GraphMethods{
	private static int totalTime;
	/**
	 * Tar en graf samt två noder och returnerar true om det finns en väg genom
	 * grafen från den ena noden till den andra (eventuellt över många andra noder), annars 
	 * returneras false. Om någon av noderna inte finns i grafen returneras också false. 
	 * Använder sig av en hjälpmetod för djupet-först-sökning genom en graf.
	 */
	public static <T> boolean pathExists(ListGraph<T> nodes, T from, T to) {
		ArrayList<T> visitedNodes = new ArrayList<T>();
		depthFirst(nodes, from, visitedNodes);
		if (visitedNodes.contains(to))
			return true;
		else
			return false;
	} //pathExists

	public static <T> void depthFirst(ListGraph<T> nodes, T from, ArrayList<T> visitedNodes){
		visitedNodes.add(from);
		T next = null;
		for (Set<Edge<T>> edgeSet : nodes.getEdgesFrom(from)){
			for(Edge<T> e : edgeSet){
				if(!visitedNodes.contains(e.getDestination())){
					next = e.getDestination();
					depthFirst(nodes, next, visitedNodes);
				}
			}
		}
	}
	
	/**
	 * Tar en graf och två noder och returnerar en lista (java.util.List) med bågar som 
	 * representerar den snabbaste (eller kortaste beroende på hur man tolkar bågarnas 
	 * vikter) vägen mellan dessa noder genom grafen, eller null om det inte finns någon väg 
	 * mellan dessa två noder. Använder alltså först metoden pathExists ovan för att testa om en väg 
	 * finns. Om den finns så används Dijkstras algoritm för att hitta den kortaste vägen, men 
	 * modifierad så att svaret blir just vägen (representerad som en lista av bågar), inte bara tiden.
	 */
	public static <T> ArrayList<Dijkstra<T>> shortestPath(ListGraph<T> nodes, T from, T to) {
		if (!pathExists(nodes, from, to))
			throw new NoSuchElementException("Det finns ingen förbindelse mellan de valda noderna.");
		else{
			HashMap<T, Dijkstra<T>> dMap = new HashMap<>();
			
			for (T t : nodes.getNodes()){
				dMap.put(t, new Dijkstra<T>());
			}

			dMap.get(from).setTime(0);
			dMap.get(from).setDone(true);
			
			T current = from;
			
			while(!dMap.get(to).getDone()){
				for(HashSet<Edge<T>> dSet : nodes.getEdgesFrom(current)){
					for (Edge<T> e : dSet){
						if (dMap.get(current).getTime() + e.getWeight() < dMap.get(e.getDestination()).getTime()){
							dMap.get(e.getDestination()).setTime(dMap.get(current).getTime() + e.getWeight());
							dMap.get(e.getDestination()).setEdge(e);
							dMap.get(e.getDestination()).setFrom(current);
						}
					}
				}
				int lowestTime = Integer.MAX_VALUE;
				T lowestNode = null;
				for (T t : nodes.getNodes()){
					if (!dMap.get(t).getDone() && dMap.get(t).getTime() < lowestTime){
						lowestTime = dMap.get(t).getTime();
						lowestNode = t;
					}
				}
				dMap.get(lowestNode).setDone(true);
				current = lowestNode;
				totalTime = dMap.get(current).getTime();
			}
			
			ArrayList<Dijkstra<T>> path = new ArrayList<>();
			T cn = to;
			while (cn != from){
				path.add(dMap.get(cn));
				cn = (T) dMap.get(cn).getFrom();
			}
			Collections.reverse(path);
			return path;	
		}
	}
	
	public static int getTotalTime(){
		return totalTime;
	}
	
}

class Dijkstra<T>{
	private int time;
	private boolean done;
	private T from;
	private Edge<T> e;
	
	Dijkstra(){
		time = Integer.MAX_VALUE;
		done = false;
		from = null;
		e = null;
	}
	
	public void setTime(int time){
		this.time = time;
	}
	public void setDone(boolean done){
		this.done = done;
	}
	public void setFrom(T from){
		this.from = from;
	}
	public void setEdge(Edge<T> e){
		this.e = e;
	}
	public int getTime(){
		return time;
	}
	public boolean getDone(){
		return done;
	}
	public T getFrom(){
		return from;
	}
	public Edge<T> getEdge(){
		return e;
	}
	public String toString(){
		return "" + e;
	}
}


