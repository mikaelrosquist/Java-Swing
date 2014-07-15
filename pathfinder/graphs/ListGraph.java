package graphs;

import java.util.*;

import graphs.Edge;

public class ListGraph<T> implements Graph<T>{ 	

	private Map<T, Set<Edge<T>>> nodes;
	
	public ListGraph(){
		nodes = new HashMap<T, Set<Edge<T>>>();
	}

	/**
	 * Tar emot en nod och stoppar in den i grafen. Om noden redan finns i grafen blir det ingen f�r�ndring.
	 */
	public void add(T node){
		nodes.put(node, new HashSet<Edge<T>>());
		System.out.println("Tillagda noder: " + nodes.keySet());
	} //add

	/**
	 * Tar tv� noder, en str�ng (namnet p� f�rbindelsen) och ett heltal (f�rbindelsens vikt)
	 * och kopplar ihop dessa noder med b�gar med detta namn och denna vikt. Om n�gon av noderna 
	 * saknas i grafen ska undantaget NoSuchElementException fr�n paketet java.util
	 * genereras. Om vikten �r negativ ska undantaget IllegalArgumentException genereras. 
	 * Om en b�ge med samma namn redan finns mellan dessa tv� noder ska undantaget 
	 * IllegalStateException genereras.
	 * Obs att grafen ska vara oriktad, d.v.s. b�gar riktade mot den andra noden m�ste stoppas in hos de
	 * b�da noderna. I en oriktad graf f�rekommer ju alltid b�garna i par: fr�n nod 1 till nod 2 och
	 * tv�rtom.
	 */
	public void connect(T from, T to, String name, int weight){

		Edge<T> e1 = new Edge<T>(to, name, weight); 
		Edge<T> e2 = new Edge<T>(from, name, weight);

		nodes.get(from).add(e1);
		nodes.get(to).add(e2);

		System.out.println(e1.getName() + " fr�n " + from + " till " + e1.getDestination() + " tar " + e1.getWeight() + " timmar.");
		System.out.println(e2.getName() + " fr�n " + to + " till " + e2.getDestination() + " tar " + e2.getWeight() + " timmar.");
	} //connect

	/**
	 * Tar tv� noder, en str�ng (namnet p� f�rbindelsen) och ett heltal
	 * (f�rbindelsens nya vikt) och s�tter denna vikt som den nya vikten hos den namngivna 
	 * f�rbindelsen mellan dessa tv� noder. Om n�gon av noderna saknas i grafen eller ingen b�ge 
	 * med det angivna namnet finns mellan dessa tv� noder ska undantaget 
	 * NoSuchElementException fr�n paketet java.util genereras. Om vikten �r negativ 
	 * ska undantaget IllegalArgumentException genereras.
	 */
	public void setConnectionWeight(T from, T to, String name, int weight) {
		
		for(Edge<T> e : getEdgesBetween(to, from)){
			if((e.getDestination().equals(to) || e.getDestination().equals(from)) && e.getName().equals(name)){
				e.setWeight(weight);
			}
		}
		
		for(Edge<T> e : getEdgesBetween(from, to)){
			if((e.getDestination().equals(to) || e.getDestination().equals(from)) && e.getName().equals(name)){
				e.setWeight(weight);
			}
		}
		
	} //setConnectionWeight


	/**
	 * Returnerar en kopia av m�ngden av alla noder.
	 */
	public List<T> getNodes() {
		return new ArrayList<T>(nodes.keySet());
	} //getNodes

	/**
	 * Tar en nod och returnerar en kopia av samlingen av alla b�gar som leder
	 * fr�n denna nod. Om noden saknas i grafen ska undantaget NoSuchElementException
	 * genereras.
	 */
	public Set<HashSet<Edge<T>>> getEdgesFrom(T node){

		Set<HashSet<Edge<T>>> set = new HashSet<HashSet<Edge<T>>>();
		HashSet<Edge<T>> set2 = new HashSet<Edge<T>>(nodes.get(node));

		set.add(set2);
		return set;
	} //getEdgesFrom

	/**
	 * Tar tv� noder och returnerar en lista av b�gar mellan dessa noder (det
	 * kan ju finnas flera b�gar mellan samma noder). Om n�gon av noderna saknas i grafen ska 
	 * undantaget NoSuchElementException genereras. Om det inte finns n�gon b�ge mellan 
	 * noderna resturneras en tom lista.
	 */
	public Set<Edge<T>> getEdgesBetween(T t1, T t2){

		Set<Edge<T>> edges = new HashSet<Edge<T>>();
		for (Edge<T> edge : nodes.get(t1))
			if (edge.getDestination().equals(t2))
				edges.add(edge);
		return edges;
	}

	/**
	 * Returnerar en l�ng str�ng med str�ngar tagna fr�n nodernas toStringmetoder och b�garnas 
	 * toString-metoder, g�rna med radbrytningar s� att man f�r information om en nod per 
	 * rad f�r f�rb�ttrad l�slighet.
	 */
	public String toString() {
		return "";
	} //toString

	public void newMap(){
		nodes = new HashMap<T, Set<Edge<T>>>();
	}
}




