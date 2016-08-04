package com.as.text_understanding.common;

import java.util.Collections;

/**
 * Common utility functions for this project.
 * 
 * 
 * <br/><br/>
 * Date: 28 February 2016
 * @author Asher Stern
 * 
 */
public class TextUnderstandingUtilities
{
	/**
	 * The output of this method is its input, unless the input is null, for
	 * which the function returns an empty Iterable.
	 * <br/>
	 * Used to shorten code like <code>for (X x : I)</code>, such that it will
	 * not be required to check for null before the loop.
	 * 
	 * @param originalIterable
	 * @return
	 */
	public static <T> Iterable<T> each(Iterable<T> originalIterable)
	{
		if (originalIterable==null)
		{
			return Collections.emptyList();
		}
		else
		{
			return originalIterable;
		}
	}


}
