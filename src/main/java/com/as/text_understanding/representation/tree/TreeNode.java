package com.as.text_understanding.representation.tree;

import java.util.List;

/**
 * 
 *
 * Date: Feb 24, 2016
 * @author Asher Stern
 *
 */
public class TreeNode
{
	public TreeNode(TreeItem item, List<TreeNode> children)
	{
		super();
		this.item = item;
		this.children = children;
	}
	
	
	
	public TreeItem getItem()
	{
		return item;
	}
	
	public List<TreeNode> getChildren()
	{
		return children;
	}

	private final TreeItem item;
	private final List<TreeNode> children;
}
