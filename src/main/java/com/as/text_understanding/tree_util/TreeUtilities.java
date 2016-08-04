package com.as.text_understanding.tree_util;

import static com.as.text_understanding.common.TextUnderstandingUtilities.each;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.as.text_understanding.representation.tree.Terminal;
import com.as.text_understanding.representation.tree.Tree;
import com.as.text_understanding.representation.tree.TreeNode;

/**
 * 
 * @author Asher Stern
 * Date: 28 February 2016
 */
public class TreeUtilities
{
	public static void treeToYield(TreeNode node, LinkedList<Terminal> terminals)
	{
		if (node.getItem().isTerminal())
		{
			terminals.add(node.getItem().getTerminal());
		}
		for (TreeNode child : each(node.getChildren()))
		{
			treeToYield(child, terminals);
		}
	}
	
	public static LinkedList<Terminal> treeToYield(TreeNode tree)
	{
		LinkedList<Terminal> terminals = new LinkedList<>();
		treeToYield(tree,terminals);
		return terminals;
	}

	public static LinkedList<Terminal> treeToYield(final Tree tree)
	{
		return treeToYield(tree.getRoot());
	}

	public static String yieldToString(List<Terminal> yield)
	{
		StringBuilder sb = new StringBuilder();
		boolean firstIteration = true;
		for (Terminal terminal : yield)
		{
			if (firstIteration) {firstIteration=false;}
			else
			{
				sb.append(" ");
			}
			sb.append(terminal.getToken());
		}
		return sb.toString();
	}
	
	public static String treeToString(final Tree tree)
	{
		return treeToString(tree.getRoot());
	}
	
	public static String treeToString(final TreeNode node)
	{
		return treeToString(node, 0);
	}
	
	public static String treeToElegantString(final TreeNode node)
	{
		return treeToElegantString(node, new boolean[0]);
	}
	
	private static String treeToString(final TreeNode node, final int indentation)
	{
		StringBuilder sb = new StringBuilder();
		char[] spaces = new char[indentation];
		Arrays.fill(spaces, ' ');
		sb.append(new String(spaces));
		if (node.getItem().isTerminal())
		{
			sb.append(node.getItem().getTerminal().getToken()).append("/").append(node.getItem().getTerminal().getTag()).append("\n");
		}
		else
		{
			sb.append(node.getItem().getSymbol()).append("\n");
			for (TreeNode child : node.getChildren())
			{
				sb.append(treeToString(child, indentation+1));
			}
		}
		return sb.toString();
	}

	private static String treeToElegantString(final TreeNode node, boolean[] ancestors)
	{
		StringBuilder sb = new StringBuilder();
		for (int index=0; index<ancestors.length; ++index)
		{
			if (index<(ancestors.length-1))
			{
				if (ancestors[index]==true)
				{
					sb.append("| ");
				}
				else
				{
					sb.append("  ");
				}
			}
			else
			{
				sb.append("+-");
			}
		}
		
		if (node.getItem().isTerminal())
		{
			sb.append(node.getItem().getTerminal().getToken()).append("/").append(node.getItem().getTerminal().getTag()).append("\n");
		}
		else
		{
			sb.append(node.getItem().getSymbol()).append("\n");
			
			final int numberOfChildren = node.getChildren().size();
			int childIndex=1;
			for (TreeNode child : node.getChildren())
			{
				boolean[] childAncestors = Arrays.copyOf(ancestors, ancestors.length+1);
				childAncestors[ancestors.length] = (childIndex<numberOfChildren);
				sb.append(treeToElegantString(child, childAncestors));
				++childIndex;
			}
		}
		return sb.toString();
	}

}
