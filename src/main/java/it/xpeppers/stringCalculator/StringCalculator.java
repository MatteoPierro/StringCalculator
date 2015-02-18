package it.xpeppers.stringCalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.xpeppers.stringCalculator.exception.NegativeNumberException;

public class StringCalculator {

	private static String DEFAULT_DELIMITER = ",|";

	public int add(String string) throws NegativeNumberException {
		String delimiters = DEFAULT_DELIMITER;
		String numbers = string;
		if (string.startsWith("//")) {
			int newLineIndex = string.indexOf("\n");
			String surroundedDelimiters = string.substring(2, newLineIndex);
			if (surroundedDelimiters.contains("["))
				delimiters = extractDelimiters(surroundedDelimiters,
						new String());
			else
				delimiters = surroundedDelimiters + "|";
			numbers = string.substring(newLineIndex + 1);
		}
		String regex = delimiters + "\n";
		List<String> numbersArray = Arrays.asList(numbers.split(regex));
		List<Integer> negativeNumbers = new ArrayList<Integer>();
		return addNumbers(numbersArray, negativeNumbers);
	}

	private String extractDelimiters(String surroundedDelimiters,
			String delimiters) {
		if (surroundedDelimiters.isEmpty())
			return delimiters;

		int initDelimiterPosition = surroundedDelimiters.indexOf("[");
		int endDelimiterPosition = surroundedDelimiters.indexOf("]");
		String extractedDelimiter = surroundedDelimiters.substring(
				initDelimiterPosition +1, endDelimiterPosition);
		String delimiter = getRegexForDelimiter(extractedDelimiter);
		String tailSurroundedDelimiter = surroundedDelimiters
				.substring(endDelimiterPosition + 1);
		return extractDelimiters(tailSurroundedDelimiter, delimiters
				+ delimiter + "|");
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
		if (numbersArray.size() == 0
				|| (numbersArray.size() == 1 && numbersArray.get(0).isEmpty())) {
			if (!negativeNumbers.isEmpty())
				throw new NegativeNumberException(negativeNumbers);
			return 0;
		} else {
			String numberString = numbersArray.get(0);
			Integer number = Integer.parseInt(numberString);
			if (number < 0)
				negativeNumbers.add(number);
			if (number > 1000)
				number = 0;
			return number
					+ addNumbers(numbersArray.subList(1, numbersArray.size()),
							negativeNumbers);
		}
	}

}
