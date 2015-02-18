package it.xpeppers.stringCalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.xpeppers.stringCalculator.exception.NegativeNumberException;

public class StringCalculator {

	public int add(String string) throws NegativeNumberException {
		return addNumbers(extractNumbersList(string), new ArrayList<Integer>());
	}

	private List<String> extractNumbersList(String string) {		
		return Arrays.asList(extractNumbersString(string).split(generateRegex(string)));
	}

	private String generateRegex(String string) {
		String regex = ",|";
		if (string.startsWith("//")) {
			String delimitersString = string.substring(2, string.indexOf("\n"));
			if (delimitersString.contains("["))
				regex = generateRegexFromDelimiters(delimitersString,
						new String());
			else
				regex = delimitersString + "|";
		}
		regex += "\n";
		return regex;
	}

	private String extractNumbersString(String string) {
		if (string.startsWith("//"))
			return string.substring(string.indexOf("\n") + 1);
		return string;
	}

	private String generateRegexFromDelimiters(String surroundedDelimiters,
			String regex) {
		if (surroundedDelimiters.isEmpty())
			return regex;

		return generateRegexFromDelimiters(surroundedDelimiters
				.substring(surroundedDelimiters.indexOf("]") + 1), regex
				+ getRegexForDelimiter(extractDelimiter(surroundedDelimiters)) + "|");
	}
	
	private String extractDelimiter(String surroundedDelimiters){
		return surroundedDelimiters.substring(
				surroundedDelimiters.indexOf("[") + 1, surroundedDelimiters.indexOf("]"));
	}

	private String getRegexForDelimiter(String delimiter) {
		StringBuilder regexForDelimiter = new StringBuilder("(");
		for (int i = 0; i < delimiter.length(); i++) {
			regexForDelimiter.append("\\" + delimiter.charAt(i));
		}
		return regexForDelimiter.append(")").toString();
	}

	private int addNumbers(List<String> numbersArray,
			List<Integer> negativeNumbers) throws NegativeNumberException {
		if (isLastIteration(numbersArray)) {
			checkNegativeNumber(negativeNumbers);
			return 0;
		} else {
			if (convertStringToInt(numbersArray.get(0)) < 0)
				negativeNumbers.add(convertStringToInt(numbersArray.get(0)));

			return threshold(convertStringToInt(numbersArray.get(0)))
					+ addNumbers(numbersArray.subList(1, numbersArray.size()),
							negativeNumbers);
		}
	}

	private int threshold(int number) {
		if (number > 1000)
			return 0;
		return number;
	}

	private int convertStringToInt(String number) {
		return Integer.parseInt(number);
	}

	private void checkNegativeNumber(List<Integer> negativeNumbers)
			throws NegativeNumberException {
		if (!negativeNumbers.isEmpty())
			throw new NegativeNumberException(negativeNumbers);
	}

	private boolean isLastIteration(List<String> numbersArray) {
		return numbersArray.size() == 0
				|| (numbersArray.size() == 1 && numbersArray.get(0).isEmpty());
	}

}
