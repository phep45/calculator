package calculator;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RPNConverterTest {

	private String input = "3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3";
	private String rpnForm = "3 4 2 * 1 5 - 2 3 ^ ^ / +";
	
	
	
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testA() throws Exception {
		input = "( 5 + 3 ) * 12 / 3";
		rpnForm = "5 3 + 12 * 3 /";

		List<String> expected = Arrays.asList(rpnForm.split(" "));

		RPNConverter converter = new RPNConverter(input);
		assertEquals(expected, converter.getAsRPN());

	}

	@Test
	public final void testB() throws Exception {
		input = "( 4 + 8 ) / 2 * 3 / ( 3 * ( 2 + 2 ) )";
		rpnForm = "4 8 + 2 / 3 * 3 2 2 + * /";
		
		List<String> expected = Arrays.asList(rpnForm.split(" "));
		
		RPNConverter converter = new RPNConverter(input);
		assertEquals(expected, converter.getAsRPN());
	}

}
