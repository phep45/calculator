package calculator;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RPNCalculatorTest {

	public final double DELTA = 0.00001;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testA() throws Exception {
		String input = "5 + ( ( 1 + 2 ) * 4 )";
		RPNConverter converter = new RPNConverter(input);
		RPNCalculator calc = new RPNCalculator(converter.getAsRPN());
		double result = calc.compute();
		assertEquals(17.0, result, DELTA);
	}
	
	@Test
	public final void testB() throws Exception {
		String input = "2 + -2 * 2";
		RPNConverter converter = new RPNConverter(input);
		RPNCalculator calc = new RPNCalculator(converter.getAsRPN());
		double result = calc.compute();
		assertEquals(-2.0, result, DELTA);
	}

}
