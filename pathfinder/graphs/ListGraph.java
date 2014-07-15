package graphs;

import java.util.*;

import graphs.Edge;

public class ListGraph<T> implements Graph<T>{ 	

	private Map<T, Set<Edge<T>>> nodes;
	
	public ListGraph(){
		nodes = new HashMap<T, Set<Edge<T>>>();
	}

	/**
	 * Tar emot en nod och stoppar in den i grafen. Om noden redan finns i grafen blir det ingen förändring.
	 */
	public void add(T node){
		nodes.put(node, new HashSet<Edge<T>>());
		System.out.println("Tillagda noder: " + nodes.keySet());
	} //add

	/**
	 * Tar två noder, en sträng (namnet på förbindelsen) och ett heltal (förbindelsens vikt)
	 * och kopplar ihop dessa noder med bågar med detta namn och denna vikt. Om någon av noderna 
	 * saknas i grafen ska undantaget NoSuchElementException från paketet java.util
	 * genereras. Om vikten är negativ ska undantaget IllegalArgumentException genereras. 
	 * Om en båge med samma namn redan finns mellan dessa två noder ska undantaget 
	 * IllegalStateException genereras.
	 * Obs att grafen ska vara oriktad, d.v.s. bågar riktade mot den andra noden måste stoppas in hos de
	 * båda noderna. I en oriktad graf förekommer ju alltid bågarna i par: från nod 1 till nod 2 och
	 * tvärtom.
	 */
	public void connect(T from, T to, String name, int weight){

		Edge<T> e1 = new Edge<T>(to, name, weight); 
		Edge<T> e2 = new Edge<T>(from, name, weight);

		nodes.get(from).add(e1);
		nodes.get(to).add(e2);

		System.out.println(e1.getName() + " från " + from + " till " + e1.getDestination() + " tar " + e1.getWeight() + " timmar.");
		System.out.println(e2.getName() + " från " + to + " till " + e2.getDestination() + " tar " + e2.getWeight() + " timmar.");
	} //connect

	/**
	 * Tar två noder, en sträng (namnet på förbindelsen) och ett heltal
	 * (förbindelsens nya vikt) och sätter denna vikt som den nya vikten hos den namngivna 
	 * förbindelsen mellan dessa två noder. Om någon av noderna saknas i grafen eller ingen båge 
	 * med det angivna namnet finns mellan dessa två noder ska undantaget 
	 * NoSuchElementException från paketet java.util genereras. Om vikten är negativ 
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
	 * Returnerar en kopia av mängden av alla noder.
	 */
	public List<T> getNodes() {
		return new ArrayList<T>(nodes.keySet());
	} //getNodes

	/**
	 * Tar en nod och returnerar en kopia av samlingen av alla bågar som leder
	 * från denna nod. Om noden saknas i grafen ska undantaget NoSuchElementException
	 * genereras.
	 */
	public Set<HashSet<Edge<T>>> getEdgesFrom(T node){

		Set<HashSet<Edge<T>>> set = new HashSet<HashSet<Edge<T>>>();
		HashSet<Edge<T>> set2 = new HashSet<Edge<T>>(nodes.get(node));

		set.add(set2);
		return set;
	} //getEdgesFrom

	/**
	 * Tar två noder och returnerar en lista av bågar mellan dessa noder (det
	 * kan ju finnas flera bågar mellan samma noder). Om någon av noderna saknas i grafen ska 
	 * undantaget NoSuchElementException genereras. Om det inte finns någon båge mellan 
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
	 * Returnerar en lång sträng med strängar tagna från nodernas toStringmetoder och bågarnas 
	 * toString-metoder, gärna med radbrytningar så att man får information om en nod per 
	 * rad för förbättrad läslighet.
	 */
	public String toString() {
		return "";
	} //toString

	public void newMap(){
		nodes = new HashMap<T, Set<Edge<T>>>();
	}
}




