package alda.heap;

import static org.junit.Assert.*;

import java.util.PriorityQueue;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class DHeapTester {

	private DHeap<Integer> heap = new DHeap<Integer>(4);

	/**
	 * Detta test kontrollerar att er kod implementerar en fungerande
	 * prioritetsko. Detta test ska naturligtvis fungera.
	 */
	@Test
	public void testFunctionality() {
		Random rnd = new Random();
		PriorityQueue<Integer> oracle = new PriorityQueue<Integer>();

		assertEquals(oracle.isEmpty(), heap.isEmpty());

		for (int n = 0; n < 1000; n++) {
			int tal = rnd.nextInt(1000);
			heap.insert(tal);
			oracle.add(tal);

			while (!heap.isEmpty() && rnd.nextBoolean()) {
				assertEquals(oracle.poll(), heap.deleteMin());
			}

			assertEquals(oracle.isEmpty(), heap.isEmpty());
		}
	}

	/**
	 * Er heap ska ha tva konstruktorer: en som inte tar nagra parametrar och
	 * som skapar en vanlig binar heap, och en som tar ett argument: d.
	 * Observera att detta betyder att ni maste forandra de konstruktorer som
	 * finns i ursprungskoden eftersom det redan finns en konstruktor som tar
	 * ett heltalsargument.
	 * 
	 * Den tredje konstruktorn i orginalversionen far ni göra som ni vill med,
	 * den ar inte viktig for uppgiften, sa plocka bort den om ni inte absolut
	 * vill fixa den ocksa.
	 */
	@Test
	public void testConstructors() {
		heap = new DHeap<Integer>(); // Skapar en binarheap
		heap = new DHeap<Integer>(2); // Skapar ytterligare en binarheap
		heap = new DHeap<Integer>(3); // Skapar en 3-heap, dvs en heap dar varje
										// nod har 3 barn
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTooSmallD() {
		new DHeap<Integer>(1);
	}

	/**
	 * Detta test förutsatter att ni lagger till en metod i heapen far att rakna
	 * ut index for en nods foralder. Detta ar inte nodvandigt for att losa
	 * uppgiften, sa om ni vill kan ni strunta i testet. Det ar dock inget vi
	 * rekommenderar eftersom metoden gor problemet lattare att losa.
	 */
	@Test
	public void testParentIndex() {
		assertEquals(5, heap.parentIndex(18));
		assertEquals(5, heap.parentIndex(21));
		assertEquals(6, heap.parentIndex(22));
		assertEquals(2, heap.parentIndex(6));
		assertEquals(1, heap.parentIndex(2));
		heap = new DHeap<Integer>();
		assertEquals(1, heap.parentIndex(2));
		assertEquals(1, heap.parentIndex(3));
		assertEquals(4, heap.parentIndex(8));
		assertEquals(4, heap.parentIndex(9));
		assertEquals(6, heap.parentIndex(12));
		assertEquals(6, heap.parentIndex(13));
		heap = new DHeap<Integer>(3);
		assertEquals(6, heap.parentIndex(17));
		assertEquals(3, heap.parentIndex(9));
		assertEquals(4, heap.parentIndex(13));
		assertEquals(1, heap.parentIndex(3));
	}

	/**
	 * aven detta test forutsatter att ni gor en metod far att rakna ut
	 * foralderns index och kan ignorerars om ni inte gor det.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testTooLowParentIndex() {
		heap.parentIndex(1);
	}

	/**
	 * Detta test forutsatter att ni lagger till en metod i heapen for att rakna
	 * ut index for en nods forsta barn. Detta ar inte nodvandigt for att losa
	 * uppgiften, sa om ni vill kan ni strunta i testet. Det ar dock inget vi
	 * rekommenderar eftersom metoden gor problemet lattare att losa.
	 */
	@Test
	public void testFirstChildIndex() {
		assertEquals(2, heap.firstChildIndex(1));
		assertEquals(6, heap.firstChildIndex(2));
		assertEquals(18, heap.firstChildIndex(5));
		assertEquals(22, heap.firstChildIndex(6));
		assertEquals(26, heap.firstChildIndex(7));
		heap = new DHeap<Integer>();
		assertEquals(2, heap.firstChildIndex(1));
		assertEquals(4, heap.firstChildIndex(2));
		assertEquals(6, heap.firstChildIndex(3));
		assertEquals(8, heap.firstChildIndex(4));
		heap = new DHeap<Integer>(3);
		assertEquals(2, heap.firstChildIndex(1));
		assertEquals(5, heap.firstChildIndex(2));
		assertEquals(11, heap.firstChildIndex(4));
		assertEquals(17, heap.firstChildIndex(6));

	}

	/**
	 * aven detta test forutatter att ni gor en metod for att rakna ut det
	 * forsta barnets index och kan ignorerars om ni inte gor det.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testTooLowChildIndex() {
		heap.firstChildIndex(0);
	}

	private void testValues(Integer... expected) {
		assertEquals(expected.length, heap.size());
		for (int n = 0; n < expected.length; n++)
			assertEquals(expected[n], heap.get(n + 1));
	}

	/**
	 * Detta test kraver att arrayen i heapen görs tillganglig via ett par
	 * metoder. Metoden size ar inte mycket att saga om, den borde antagligen
	 * funnits i orginalet. get daremot bryter inkapslingen ganska rejalt och ar
	 * bara till for att vi ska kunna testa. Det ar naturligtvis farkastligt,
	 * men ibland nodvandigt. Den hogsta skyddsnivan (=basta) get kan ha ar
	 * deafult-nivan. Detta test maste fungera, sa ni maste lagga till
	 * metoderna:
	 * 
	 * public int size(){ return currentSize; }
	 * 
	 * AnyType get(int index){ return array[index]; }
	 */
	@Test
	public void testContent() {
		testValues();
		heap.insert(17);
		testValues(17);
		heap.insert(23);
		testValues(17, 23);
		heap.insert(5);
		testValues(5, 23, 17);
		heap.insert(12);
		testValues(5, 23, 17, 12);
		heap.insert(100);
		heap.insert(51);
		heap.insert(52);
		testValues(5, 23, 17, 12, 100, 51, 52);
		heap.insert(4);
		testValues(4, 5, 17, 12, 100, 51, 52, 23);
		heap.insert(70);
		testValues(4, 5, 17, 12, 100, 51, 52, 23, 70);
		heap.insert(10);
		testValues(4, 5, 10, 12, 100, 51, 52, 23, 70, 17);
		heap.insert(1);
		testValues(1, 5, 4, 12, 100, 51, 52, 23, 70, 17, 10);

		assertEquals(1, (int) heap.deleteMin());
		testValues(4, 5, 10, 12, 100, 51, 52, 23, 70, 17);
		assertEquals(4, (int) heap.deleteMin());
		testValues(5, 17, 10, 12, 100, 51, 52, 23, 70);
		assertEquals(5, (int) heap.deleteMin());
		testValues(10, 17, 70, 12, 100, 51, 52, 23);
	}

}


