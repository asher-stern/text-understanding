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
		this(terminal,null);
	}
	
	public TreeItem(String symbol)
	{
		this(null, symbol);
	}
	
	public void setBeginEnd(int begin, int end)
	{
		this.begin = begin;
		this.end = end;
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
	
	
	public int getBegin()
	{
		return begin;
	}

	public int getEnd()
	{
		return end;
	}

	
	
	protected TreeItem(Terminal terminal, String symbol)
	{
		super();
		this.terminal = terminal;
		this.symbol = symbol;
	}
	
	private final Terminal terminal;
	private final String symbol;
	
	private int begin;
	private int end;
}
