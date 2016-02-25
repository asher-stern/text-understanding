package com.as.text_understanding.representation.tree;

/**
 * 
 *
 * Date: Feb 24, 2016
 * @author Asher Stern
 *
 */
public class Terminal
{
	public Terminal(String token, String tag)
	{
		super();
		this.token = token;
		this.tag = tag;
	}
	
	
	public String getToken()
	{
		return token;
	}
	public String getTag()
	{
		return tag;
	}


	private final String token;
	private final String tag;
}
