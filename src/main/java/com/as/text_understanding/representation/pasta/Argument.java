package com.as.text_understanding.representation.pasta;

import com.as.text_understanding.representation.tree.TreeNode;

/**
 * 
 *
 * Date: Feb 24, 2016
 * @author Asher Stern
 *
 */
public class Argument
{
	public Argument(ArgumentType type, boolean clause, TreeNode subtree)
	{
		super();
		this.type = type;
		this.clause = clause;
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
	public TreeNode getSubtree()
	{
		return subtree;
	}



	private final ArgumentType type;
	private final boolean clause;
	private final TreeNode subtree;
}
