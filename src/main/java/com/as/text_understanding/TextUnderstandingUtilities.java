package com.as.text_understanding;

import java.util.Collections;

/**
 * 
 * @author Asher Stern
 * Date: 28 February 2016
 */
public class TextUnderstandingUtilities
{
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
