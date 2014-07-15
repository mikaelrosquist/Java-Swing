package graphs;

import java.util.*;

public class GraphMethods{
	private static int totalTime;
	/**
	 * Tar en graf samt tv� noder och returnerar true om det finns en v�g genom
	 * grafen fr�n den ena noden till den andra (eventuellt �ver m�nga andra noder), annars 
	 * returneras false. Om n�gon av noderna inte finns i grafen returneras ocks� false. 
	 * Anv�nder sig av en hj�lpmetod f�r djupet-f�rst-s�kning genom en graf.
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
	 * Tar en graf och tv� noder och returnerar en lista (java.util.List) med b�gar som 
	 * representerar den snabbaste (eller kortaste beroende p� hur man tolkar b�garnas 
	 * vikter) v�gen mellan dessa noder genom grafen, eller null om det inte finns n�gon v�g 
	 * mellan dessa tv� noder. Anv�nder allts� f�rst metoden pathExists ovan f�r att testa om en v�g 
	 * finns. Om den finns s� anv�nds Dijkstras algoritm f�r att hitta den kortaste v�gen, men 
	 * modifierad s� att svaret blir just v�gen (representerad som en lista av b�gar), inte bara tiden.
	 */
	public static <T> ArrayList<Dijkstra<T>> shortestPath(ListGraph<T> nodes, T from, T to) {
		if (!pathExists(nodes, from, to))
			throw new NoSuchElementException("Det finns ingen f�rbindelse mellan de valda noderna.");
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


