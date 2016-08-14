package com.as.text_understanding.representation.tree;

import com.as.text_understanding.common.TextUnderstandingException;

/**
 * In a constituency parse-tree each tree-node is either a terminal or a non-terminal. In case it is a terminal it contains
 * a token (a word) and its part-of-speech. In case it is a non-terminal in contains a symbol, like "VP", "NP", etc.
 * <br/>
 * The tree-item contains that information, for either terminal or non-terminal node. It has getters: for terminal {@link #getTerminal()},
 * for non-terminal {@link #getSymbol()}. Note that if this {@link TreeItem} represents a terminal ,the non-terminal getter would
 * return some undefined result, and must not be called. Similarly, if it represents a non-terminal, the {@link #getTerminal()} must not
 * be called (it will also return something undefined).
 *
 * <br/>
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
	
	/**
	 * Set the character-indexes in the sentence of the beginning of the first terminal and the end of the last terminal that are
	 * dominated by this node. 
	 * @param begin
	 * @param end
	 */
	public void setBeginEnd(int begin, int end)
	{
		this.begin = begin;
		this.end = end;
		beginEndSet = true;
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
		if (!beginEndSet) throw new TextUnderstandingException("Begin and End were not set.");
		return begin;
	}

	public int getEnd()
	{
		if (!beginEndSet) throw new TextUnderstandingException("Begin and End were not set.");
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
	
	private boolean beginEndSet = false;
	private int begin;
	private int end;
}
