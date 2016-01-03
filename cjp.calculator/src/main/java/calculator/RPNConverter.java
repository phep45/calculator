package calculator;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

class RPNConverter {
	private String input;
	private Stack<String> operatorStack = new Stack<String>();
	private List<String> output = new LinkedList<String>();

	static Logger log = Logger.getLogger(RPNConverter.class.getName());
	static {
		log.setLevel(Level.TRACE);
	}
	
	public RPNConverter(String input) {
		this.input = input;
		BasicConfigurator.configure();
	}

	public List<String> getAsRPN() throws Exception {

		String[] tokens = input.split(" ");

		for(String token : tokens) {
			log.trace("token: " + token);
			try {
				Double.parseDouble(token);
				output.add(token);
				log.trace("numeric token " + token + " added to output");
			} catch (NumberFormatException e) {
				try {
					if(plusOrMinus(token) && multiplyOrDivide(operatorStack.peek())) {
						while(!operatorStack.isEmpty()) {
							log.trace(operatorStack.peek() + " popped from stack and added to output");
							output.add(operatorStack.pop());
						}
						operatorStack.push(token);
					}
					else if(plusOrMinus(token) && plusOrMinus(operatorStack.peek())) {
						log.trace(operatorStack.peek() + " popped from stack and added to output");
						output.add(operatorStack.pop());
						log.trace("operator token " + token + " pushed to stack");
						operatorStack.push(token);
					}
					else if(multiplyOrDivide(token) && multiplyOrDivide(operatorStack.peek())) {
						log.trace(operatorStack.peek() + " popped from stack and added to output");
						output.add(operatorStack.pop());
						log.trace("operator token " + token + " pushed to stack");
						operatorStack.push(token);
					}
//					else if(multiplyOrDivide(token) && plusOrMinus(operatorStack.peek())) {
//						while(!operatorStack.isEmpty()) {
//							log.trace(operatorStack.peek() + " popped from stack and added to output");
//							output.add(operatorStack.pop());
//						}
//					}
					else if(token.equals("(")) {
						log.trace("operator token " + token + " pushed to stack");
						operatorStack.push(token);
					}
					else if(token.equals(")")) {
						log.info("operator stack: " + operatorStack);
						while(true){
							if(!operatorStack.peek().equals("(") && !operatorStack.isEmpty()) {
								log.trace(operatorStack.peek() + " popped from stack and added to output");
								output.add(operatorStack.pop());
							}
							else {
								log.trace(operatorStack.peek() + " popped from stack");
								operatorStack.pop();
								break;
							}
						}
					}
					else if(plusOrMinus(token) || multiplyOrDivide(token)) {
						log.trace("operator token " + token + " pushed to stack");
						operatorStack.push(token);
					}
				} catch(EmptyStackException e2) {
					log.trace("operator token " + token + " pushed to stack");
					operatorStack.push(token);
				}
			}


		}
		
		log.trace("popping rest of stack to output");
		while(!operatorStack.isEmpty()) {
			log.trace(operatorStack.peek() + " popped from stack and added to output");
			output.add(operatorStack.pop());
		}
		for(String token : output) {
			if(token.equalsIgnoreCase("(") || token.equals(")")){
				throw new Exception("Problem with parenthesis");
			}
		}
		return output;
	}

	private boolean plusOrMinus(String token) {
		if((token.equals("+") || token.equals("-"))) {
			return true;
		}
		else
			return false;
	}

	private boolean multiplyOrDivide(String token) {
		if((token.equals("*") || token.equals("/"))) {
			return true;
		}
		else
			return false;
	}
}