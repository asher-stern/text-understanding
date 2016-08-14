package com.as.text_understanding.representation.tree;

/**
 * A terminal, in a constituency tree, is a leaf - a tree-node which has no children, and represents a single word of the parsed sentence.
 * The word (the token) has a part-of-speech, also known as a tag.  
 *
 * <br/>
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
