package alda.heap;

public class DHeap<AnyType extends Comparable<? super AnyType>>
{

	private static final int DEFAULT_CAPACITY = 10;
	private int currentSize;
	private AnyType[] array;
	private int d;

	public DHeap()
	{
		this(2, DEFAULT_CAPACITY);
	}

	public DHeap(int d)
	{
		this(d, DEFAULT_CAPACITY);
	}

	public DHeap(int d, int capacity)
	{
		if (d < 2)
			throw new IllegalArgumentException();
			
		this.d = d;
		currentSize = 0;
		array = (AnyType[]) new Comparable[capacity + 1];
	}
	

	public int parentIndex(int childIndex){
		if (childIndex <= 1)
			throw new IllegalArgumentException();
			
		return (childIndex - 2) / d + 1;
	}

	public int firstChildIndex(int parentIndex){
		if (parentIndex <= 0)
			throw new IllegalArgumentException();
			
		return d * (parentIndex - 1) + 2;
	}

	public int size(){
		return currentSize;
	}

	AnyType get(int index){ 
		return array[index];
	}

	public void insert(AnyType x)
	{
		if(currentSize == array.length - 1)
			enlargeArray(array.length * 2 + 1);

		int hole = ++currentSize;
		for(; hole > 1 && x.compareTo(array[parentIndex(hole)]) < 0; hole = parentIndex(hole))
			array[hole] = array[parentIndex(hole)];
		array[hole] = x;
	}

	private void enlargeArray(int newSize)
	{
		AnyType[] old = array;
		array = (AnyType[]) new Comparable[newSize];
		for(int i = 0; i < old.length; i++)
			array[i] = old[i];        
	}
	
	public AnyType findMin()
	{
		if(isEmpty())
			throw new UnderflowException();
		return array[1];
	}

	public AnyType deleteMin()
	{
		if(isEmpty())
			throw new UnderflowException();

		AnyType minItem = findMin();
		array[1] = array[currentSize--];
		percolateDown(1);

		return minItem;
	}
	
	public boolean isEmpty()
	{
		return currentSize == 0;
	}

	public void makeEmpty()
	{
		currentSize = 0;
	}

	private void percolateDown(int hole) {
		int child;
	    AnyType tmp = array[hole];

	    for(; firstChildIndex(hole) <= currentSize; hole = child)
	    {
	    	child = firstChildIndex(hole);
	 	    int tmpChild = child; //Skapar ett temporärt barn
	    	
	        for(int i = 1; i < d && tmpChild != currentSize; tmpChild++, i++)
				if(array[tmpChild + 1].compareTo(array[child]) < 0)
						child = tmpChild + 1; 
						
	        if (array[child].compareTo(tmp) < 0)
	            array[hole] = array[child];
	        else
	            break;
	    }
	    array[hole] = tmp;
	}
}