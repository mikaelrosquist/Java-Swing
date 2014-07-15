/**
 * Ett gränssnitt Graph som deklarerar gränssnittet ListGraph. Referenser till 
 * ListGraphobjekt i uppgiften ska deklareras med typen Graph, för maximal flexibilitet.
 */

package graphs;

import java.util.*;

public interface Graph<T> {
	
	public void add(T node);
	
	public void connect(T from, T to, String n, int w);
	
	public void setConnectionWeight(T from, T to, String n, int w);
	
	public List<T> getNodes();
	
	public Set<HashSet<Edge<T>>> getEdgesFrom(T node);
	
	public Set<Edge<T>> getEdgesBetween(T t1, T t2);
	
	public String toString();

}



