package calculator;

import java.util.List;
import java.util.Stack;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class RPNCalculator {

	private List<String> expression;
	private Stack<Double> valuesStack = new Stack<Double>();
	static Logger log = Logger.getLogger(RPNCalculator.class.getName());
	static {
		log.setLevel(Level.TRACE);
	}
	
	public RPNCalculator(List<String> expression) {
		this.expression = expression;
		BasicConfigurator.configure();
	}

	public double compute() throws Exception {
		log.info("START OF COMPUTING");
		for(String token : expression) {
			log.info("token: " + token);
			try {
				valuesStack.push(Double.parseDouble(token));
				log.trace("numeric token " + token + " pushed to stack");
			} catch(NumberFormatException e) {
				double a = valuesStack.pop();
				double b = valuesStack.pop();
				if(token.equals("+")) {
					valuesStack.push(a + b);
					log.trace("operation: " + a + " + " + b);
				}
				else if(token.equals("-")) {
					valuesStack.push(a - b);
					log.trace("operation: " + a + " - " + b);
				}
				else if(token.equals("*")) {
					valuesStack.push(a * b);
					log.trace("operation: " + a + " * " + b);
				}
				else if(token.equals("/")) {
					valuesStack.push(a / b);
					log.trace("operation: " + a + " / " + b);
				}
			}
		}
		if(valuesStack.size() == 1) {
			return valuesStack.pop();
		}
		else {
			log.error("invalid expression passed");
			throw new Exception("invalid expression");
		}
	}
}
