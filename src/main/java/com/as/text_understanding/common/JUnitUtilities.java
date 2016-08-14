package com.as.text_understanding.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * Common utilities for JUnit (unit testing).
 *
 * Date: Mar 2, 2016
 * @author asher
 *
 */
public class JUnitUtilities
{
	/**
	 * Tests whether the expected list is the prefix of the actual list, where equality of elements is defined by getting the same
	 * output by the given function.
	 * @param expected an expected list
	 * @param actual an actual list
	 * @param function a function that converts elements into something.
	 */
	public static <T,R> void assertListStartsWith(List<T> expected, List<T> actual, Function<T, R> function)
	{
		assertListStartsWith(expected, actual, function, function);
	}
	
	/**
	 * Tests whether the expected list is the prefix of the actual list, where equality of elements is defined by the given functions.
	 * @param expected an expected list
	 * @param actual an actual list
	 * @param functionExpected a function that converts the elements of the expected list into something. 
	 * @param functionActual a function that converts the elements of the actual list into something.
	 */
	public static <T, U, R> void assertListStartsWith(List<T> expected, List<U> actual, Function<T, R> functionExpected, Function<U, R> functionActual)
	{
		Iterator<T> expectedIterator = expected.iterator();
		Iterator<U> actualIterator = actual.iterator();
		while (expectedIterator.hasNext())
		{
			assertTrue("Actual to short", actualIterator.hasNext());
			if (actualIterator.hasNext())
			{
				T expectedItem = expectedIterator.next();
				U actualItem = actualIterator.next();
				assertEquals("Item mismatch",functionExpected.apply(expectedItem), functionActual.apply(actualItem));
			}
		}
	}

}
