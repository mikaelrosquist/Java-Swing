package graphs;

/**
 * En klass Edge. Alla instansvariabler i klassen ska skickas som argument till konstruktorn, och
 * objekt av klassen ska inte kunna skapas av klasser utanför graphs-paketet.
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
	 * Returnerar den nod som bågen pekar ut.
	 */
	public T getDestination(){
		return destination;
	} //getDestination
	
	/**
	 * Returnerar bågens vikt.
	 */
	public int getWeight(){
		return weight;
	} //getWeight
	
	/**
	 * Tar ett heltal och sätter bågens vikt till det angivna värdet. Om vikten är
	 * negativ ska undantaget IllegalArgumentException genereras.
	 */
	public int setWeight(int weight){
		if (weight > 0)
			return this.weight = weight;
		else
            throw new IllegalArgumentException("Bågen får ej vara negativ!");
	} //setWeight
	
	/**
	 * Returnerar bågens namn.
	 */
	public String getName(){
		return name;
	} //getName
	
	/**
	 * Returnerar en sträng som innehåller information om den aktuella bågen.
	 */
	public String toString(){
		return name + " till " + getDestination() + " (" + weight + " timmar)";
	} //toString
}


