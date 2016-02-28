package com.as.text_understanding.representation.pasta;

import com.as.text_understanding.tree_travel.TreeTravelNode;

/**
 * 
 *
 * Date: Feb 24, 2016
 * @author Asher Stern
 *
 */
public class Predicate
{
	public Predicate(TreeTravelNode subtree, TreeTravelNode verbNode)
	{
		super();
		this.subtree = subtree;
		this.verbNode = verbNode;
	}
	
	
	
	public TreeTravelNode getSubtree()
	{
		return subtree;
	}
	public TreeTravelNode getVerbNode()
	{
		return verbNode;
	}



	private final TreeTravelNode subtree;
	private final TreeTravelNode verbNode;
}
