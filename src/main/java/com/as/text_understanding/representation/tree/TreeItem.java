package com.as.text_understanding.representation.tree;

/**
 * 
 *
 * Date: Feb 24, 2016
 * @author Asher Stern
 *
 */
public class TreeItem
{
	public TreeItem(Terminal terminal)
	{
		super();
		this.terminal = terminal;
		this.symbol = null;
	}
	
	public TreeItem(String symbol)
	{
		super();
		this.terminal = null;
		this.symbol = symbol;
	}
	
	public Terminal getTerminal()
	{
		return terminal;
	}
	
	public String getSymbol()
	{
		return symbol;
	}
	
	public boolean isTerminal()
	{
		return (terminal!=null);
	}
	
	
	
	protected TreeItem(Terminal terminal, String symbol)
	{
		super();
		this.terminal = terminal;
		this.symbol = symbol;
	}
	private final Terminal terminal;
	private final String symbol;
}
