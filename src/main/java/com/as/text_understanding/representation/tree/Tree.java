package com.as.text_understanding.representation.tree;


/**
 * 
 *
 * Date: Feb 24, 2016
 * @author Asher Stern
 *
 */
public class Tree
{
	public Tree(TreeNode root)
	{
		super();
		this.root = root;
	}

	public TreeNode getRoot()
	{
		return root;
	}

	private final TreeNode root;
}
