import static org.junit.Assert.*;
import org.junit.Test;

public class RomanNumbersTest {

	@Test
	public void testSingle() {
		assertEquals(1, RomanNumbers.convert("I"));
		assertEquals(5, RomanNumbers.convert("V"));
		assertEquals(10, RomanNumbers.convert("X"));
		assertEquals(50, RomanNumbers.convert("L"));
		assertEquals(100, RomanNumbers.convert("C"));
		assertEquals(500, RomanNumbers.convert("D"));
		assertEquals(1000, RomanNumbers.convert("M"));
	}

	@Test
	public void testCombined() {
		assertEquals(2, RomanNumbers.convert("II"));
		assertEquals(3, RomanNumbers.convert("III"));
		assertEquals(6, RomanNumbers.convert("VI"));
	}

	@Test
	public void testCombinedSmallFirst() {
		assertEquals(4, RomanNumbers.convert("IV"));
		assertEquals(9, RomanNumbers.convert("IX"));
		assertEquals(188, RomanNumbers.convert("CLXXXVIII"));
		assertEquals(48, RomanNumbers.convert("XLVIII"));
		assertEquals(888, RomanNumbers.convert("DCCCLXXXVIII"));
		assertEquals(1438, RomanNumbers.convert("MCDXXXVIII"));
	}

}
