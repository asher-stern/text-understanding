package com.as.text_understanding.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * 
 *
 * Date: Mar 2, 2016
 * @author asher
 *
 */
public class JUnitUtilities
{
	public static <T,R> void assertListStartsWith(List<T> expected, List<T> actual, Function<T, R> function)
	{
		assertListStartsWith(expected, actual, function, function);
	}
	
	
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
