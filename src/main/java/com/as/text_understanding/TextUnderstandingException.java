package com.as.text_understanding;


/**
 * 
 *
 * Date: Feb 24, 2016
 * @author Asher Stern
 *
 */
@SuppressWarnings("serial")
public class TextUnderstandingException extends RuntimeException
{
	public TextUnderstandingException()
	{
	}

	public TextUnderstandingException(String message)
	{
		super(message);
	}

	public TextUnderstandingException(Throwable cause)
	{
		super(cause);
	}

	public TextUnderstandingException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public TextUnderstandingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
