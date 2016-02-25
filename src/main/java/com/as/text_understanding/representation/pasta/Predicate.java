package com.as.text_understanding.representation.pasta;

import com.as.text_understanding.representation.tree.TreeNode;

/**
 * 
 *
 * Date: Feb 24, 2016
 * @author Asher Stern
 *
 */
public class Predicate
{
	public Predicate(TreeNode subtree, TreeNode verbNode)
	{
		super();
		this.subtree = subtree;
		this.verbNode = verbNode;
	}
	
	
	
	public TreeNode getSubtree()
	{
		return subtree;
	}
	public TreeNode getVerbNode()
	{
		return verbNode;
	}



	private final TreeNode subtree;
	private final TreeNode verbNode;
}
