package calculator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class UserInterface {

	static RPNCalculator calc;
	static RPNConverter converter;
	
	static Logger log = Logger.getLogger(UserInterface.class.getName());
	
	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		log.setLevel(Level.TRACE);
		RPNCalculator.log.setLevel(Level.ERROR);
		RPNConverter.log.setLevel(Level.TRACE);
		System.out.println("Every operand MUST have spaces on both sides, example:");
		System.out.println("( 2 - 4 ) * 8");
		System.out.println("------------------------------------------------------");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String expression = "";
		while(true) {
			log.debug("current expression: " + expression);
			System.out.print(">> ");
			String freshInput = reader.readLine();
			expression = new StringBuilder(expression).append(" ").append(freshInput).toString();
			if(freshInput.equals("exit")) return;
			if(!isConsistent(expression)) {
				System.err.println("expression inconsistent");
				continue;
			}
			converter = new RPNConverter(expression);
			List<String> rpn = converter.getAsRPN();
			log.debug("RPN: " + rpn);
			calc = new RPNCalculator(rpn);
			try {
				System.out.println("= " + calc.compute());
			} catch(Exception e) {
				System.err.println(e.getMessage());
				System.out.println();
			}
		}
	}
	
	private static boolean isConsistent(String expression) {
		Pattern pattern = Pattern.compile("^([\\+\\-\\*\\/\\d\\ \\)\\(\\.]*)$");
		Matcher matcher = pattern.matcher(expression);
		if(matcher.find()) {
			String[] splitted = expression.split(" ");
			return true;
		}
		return false;
	}
	
}
