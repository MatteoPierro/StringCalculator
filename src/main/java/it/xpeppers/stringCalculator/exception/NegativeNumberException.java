package it.xpeppers.stringCalculator.exception;

import java.util.List;

@SuppressWarnings("serial")
public class NegativeNumberException extends Exception {
	
	private List<Integer> negativeNumbers;
	
	public NegativeNumberException(List<Integer> negativeNumbers){
		this.negativeNumbers = negativeNumbers;
	}

	public String toString(){
		StringBuilder messageBuilder = new StringBuilder("Negatives not allowed (");
		for (Integer number : negativeNumbers){
			messageBuilder.append(number+",");
		}
		messageBuilder.append(")");
		return messageBuilder.toString();
	}
	
	@Override
	public String getMessage(){
		return toString();
	}
}
