package graphs;

/**
 * En klass Edge. Alla instansvariabler i klassen ska skickas som argument till konstruktorn, och
 * objekt av klassen ska inte kunna skapas av klasser utanf�r graphs-paketet.
 * 
 */

public class Edge<T>  {
	private T destination;
	private String name;
	private int weight;

	public Edge(T destination, String name, int weight){
		this.destination = destination;
		this.name = name;
		this.weight = weight;
	} 
	
	/**
	 * Returnerar den nod som b�gen pekar ut.
	 */
	public T getDestination(){
		return destination;
	} //getDestination
	
	/**
	 * Returnerar b�gens vikt.
	 */
	public int getWeight(){
		return weight;
	} //getWeight
	
	/**
	 * Tar ett heltal och s�tter b�gens vikt till det angivna v�rdet. Om vikten �r
	 * negativ ska undantaget IllegalArgumentException genereras.
	 */
	public int setWeight(int weight){
		if (weight > 0)
			return this.weight = weight;
		else
            throw new IllegalArgumentException("B�gen f�r ej vara negativ!");
	} //setWeight
	
	/**
	 * Returnerar b�gens namn.
	 */
	public String getName(){
		return name;
	} //getName
	
	/**
	 * Returnerar en str�ng som inneh�ller information om den aktuella b�gen.
	 */
	public String toString(){
		return name + " till " + getDestination() + " (" + weight + " timmar)";
	} //toString
}


