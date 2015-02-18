package it.xpeppers.stringCalculator;

import static org.junit.Assert.*;
import it.xpeppers.stringCalculator.StringCalculator;
import it.xpeppers.stringCalculator.exception.NegativeNumberException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StringCalculatorTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void emptyString() throws Exception{
		assertEquals(0, new StringCalculator().add(""));
	}
	
	@Test
	public void oneNumbers() throws Exception{
		assertEquals(1, new StringCalculator().add("1"));
	}
	
	@Test
	public void addTwoNumbers() throws Exception{
		assertEquals(3, new StringCalculator().add("1,2"));
	}
	
	@Test
	public void addNumbers() throws Exception{
		assertEquals(6, new StringCalculator().add("1,2,3"));
	}

	@Test
	public void addNumbersWithNewLine() throws Exception{
		assertEquals(6, new StringCalculator().add("1\n2,3"));
	}
	
	@Test
	public void addNumbersWithCustomDelimiter() throws Exception{
		assertEquals(3, new StringCalculator().add("//;\n1;2"));
	}
	
	@Test
	public void negativeNumberException() throws Exception{
		exception.equals(NegativeNumberException.class);
		exception.expectMessage("Negatives not allowed (-1,-2,)");
		StringCalculator stringCalculator = new StringCalculator();
		stringCalculator.add("1,2,-1,-2");
	}
	
	@Test
	public void addNumbersBiggerThenOneHundred() throws Exception {
		assertEquals(2, new StringCalculator().add("2,1001"));
	}
	
    @Test
	public void customDelimiterWithFreeLength() throws Exception {
		assertEquals(60, new StringCalculator().add("//[***]\n10***20***30"));
		assertEquals(60, new StringCalculator().add("//[$$]\n10$$20$$30"));
	}
    
    @Test
	public void multipleDelimiters() throws Exception {
		assertEquals(6, new StringCalculator().add("//[*][%]\n1*2%3"));
	}
    
    @Test
	public void multipleDelimitersWithFreeLength() throws Exception {
		assertEquals(6, new StringCalculator().add("//[***][%%%]\n1***2%%%3"));
	}
}
