package com.as.text_understanding.representation.pasta;

import java.util.List;

import com.as.text_understanding.tree_travel.TreeTravelNode;

/**
 * 
 *
 * Date: Feb 24, 2016
 * @author Asher Stern
 *
 */
public class Argument
{
	public Argument(ArgumentType type, boolean clause, TreeTravelNode subtree)
	{
		this(type,clause,null,subtree);
	}
	
	public Argument(ArgumentType type, boolean clause, List<TreeTravelNode> preposition, TreeTravelNode subtree)
	{
		super();
		this.type = type;
		this.clause = clause;
		this.preposition = preposition;
		this.subtree = subtree;
	}
	
	
	public ArgumentType getType()
	{
		return type;
	}
	public boolean isClause()
	{
		return clause;
	}
	public List<TreeTravelNode> getPreposition()
	{
		return preposition;
	}

	public TreeTravelNode getSubtree()
	{
		return subtree;
	}



	private final ArgumentType type;
	private final boolean clause;
	private final List<TreeTravelNode> preposition;
	private final TreeTravelNode subtree;
}
