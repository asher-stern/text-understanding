package com.as.text_understanding.representation.pasta;

import com.as.text_understanding.tree_travel.TreeTravelNode;

/**
 * Represents a subtree of a constituency tree that represents a predicate. Typically some arguments are also included in 
 * that subtree, as children of the predicate-phrase. 
 * <br/>
 * In most practical cases (at least those in which the predicate is a verb), the whole subtree is less relevant
 * than the actual verb-node (the node that contains the verb itself). This verb-node is also included in this class, as the field
 * {@link #verbNode}, that can be retrieved by the getter {@link #getVerbNode()}. 
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
	
	/**
	 * The tree-node that contains only the verb, not the verb phrase.
	 */
	private final TreeTravelNode verbNode;
}
