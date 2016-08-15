package com.as.text_understanding.tree_travel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.lang3.mutable.MutableInt;

import com.as.text_understanding.representation.tree.TreeItem;
import com.as.text_understanding.representation.tree.TreeNode;

/**
 * Represents a parse-tree node, much like {@link TreeNode}, but with additional functionality to navigate to the parent, the children
 * the descendants, the ancestors, and the siblings.
 * <p>
 * <b>Note: this class is not immutable.</b><br/>
 * <b>Note: setters must be called before any usage.</b>
 * <p>
 * A complete tree of type {@link TreeTravelNode} can be constructed by the static method {@link #createFromTree(TreeNode)}.
 * 
 * <br/>
 * Date: Feb 24, 2016
 * @author Asher Stern
 *
 */
public class TreeTravelNode
{
	public static TreeTravelNode createFromTree(TreeNode root)
	{
		return createFromTree(root, 0, new MutableInt(0));
	}
	

	
	/**
	 * Construct a {@link TreeTravelNode} that wraps a {@link TreeNode}.
	 * @param itself the wrapped tree-node
	 * @param index index of this node among its siblings. If this node is the root, or is the first child of its parent, its index
	 * is 0. If it is the second child of its parent then the index is 1. And so on. 
	 * @param terminalIndex An index, starting from 0, for all terminal-nodes in the tree. The first terminal (which is the first word
	 * in the sentence) is indexed as 0. The second 1, and so on.
	 */
	public TreeTravelNode(TreeNode itself, int index, int terminalIndex)
	{
		super();
		this.itself = itself;
		this.index = index;
		this.terminalIndex = terminalIndex;
	}

	public void setParent(TreeTravelNode parent)
	{
		this.parent = parent;
	}
	public void addChild(TreeTravelNode child)
	{
		if (children==null)
		{
			children = new ArrayList<>();
		}
		children.add(child);
	}
	public void setChildren(ArrayList<TreeTravelNode> children)
	{
		this.children = children;
	}
	
	/**
	 * Set the number of terminal-nodes governed by this node.
	 * @param numberOfCoveredChildren
	 */
	public void setNumberOfCoveredTerminals(int numberOfCoveredChildren)
	{
		this.numberOfCoveredTerminals = numberOfCoveredChildren;
	}

	public TreeNode getItself()
	{
		return itself;
	}
	public TreeTravelNode getParent()
	{
		return parent;
	}
	public ArrayList<TreeTravelNode> getChildren()
	{
		return children;
	}
	public int getIndex()
	{
		return index;
	}
	public int getTerminalIndex()
	{
		return terminalIndex;
	}
	public int getNumberOfCoveredTerminals()
	{
		return numberOfCoveredTerminals;
	}

	public ArrayList<TreeTravelNode> getAllSiblings()
	{
		if (null==parent) return null;
		return parent.getChildren();
	}
	
	public TreeTravelNode getImmediatePredecessorSibling()
	{
		if (null==parent) return null;
		if (index<=0) return null;
		return parent.getChildren().get(index-1);
	}
	
	public TreeTravelNode getImmediateNonTerminalPredecessorSibling()
	{
		if (null==parent) return null;
		ArrayList<TreeTravelNode> siblings = parent.getChildren();
		if (siblings!=null)
		{
			int siblingIndex = index-1;
			while (siblingIndex>=0)
			{
				if (!siblings.get(siblingIndex).getItself().getItem().isTerminal())
				{
					return siblings.get(siblingIndex);
				}
				--siblingIndex;
			}
		}
		return null;
	}
	
	public TreeTravelNode getImmediateSuccessorSibling()
	{
		ArrayList<TreeTravelNode> siblings = getAllSiblings();
		if (siblings!=null)
		{
			if (index<(siblings.size()-1))
			{
				return siblings.get(index+1);
			}
		}
		return null;
	}
	
	public TreeTravelNode getFirstPredecessorSibling(String symbol)
	{
		ArrayList<TreeTravelNode> siblings = getAllSiblings();
		if (siblings!=null)
		{
			for (int siblingIndex=0; siblingIndex<index; ++siblingIndex)
			{
				TreeItem item = siblings.get(siblingIndex).getItself().getItem();
				if (!item.isTerminal())
				{
					if (item.getSymbol().equals(symbol))
					{
						return siblings.get(siblingIndex);
					}
				}
			}
		}
		return null;
	}
	
	public TreeTravelNode getLastPredecessorSibling(String symbol)
	{
		ArrayList<TreeTravelNode> siblings = getAllSiblings();
		if (siblings!=null)
		{
			for (int siblingIndex=index-1; siblingIndex>=0; --siblingIndex)
			{
				TreeItem item = siblings.get(siblingIndex).getItself().getItem();
				if (!item.isTerminal())
				{
					if (item.getSymbol().equals(symbol))
					{
						return siblings.get(siblingIndex);
					}
				}
			}
		}
		return null;
	}
	
	public TreeTravelNode getFirstSibling(String symbol)
	{
		ArrayList<TreeTravelNode> siblings = getAllSiblings();
		if (siblings!=null)
		{
			for (int siblingIndex=0; siblingIndex<siblings.size(); ++siblingIndex)
			{
				if (siblingIndex!=index)
				{
					TreeTravelNode candidate = siblings.get(siblingIndex);
					if (!candidate.getItself().getItem().isTerminal())
					{
						if (candidate.getItself().getItem().getSymbol().equals(symbol))
						{
							return candidate;
						}
					}
				}
			}
		}
		return null;
	}
	
	public ArrayList<TreeTravelNode> getNonTerminalChildren()
	{
		if (children!=null)
		{
			ArrayList<TreeTravelNode> ret = new ArrayList<>(children.size());
			for (TreeTravelNode child : children)
			{
				if (!child.getItself().getItem().isTerminal())
				{
					ret.add(child);
				}
			}
			return ret;
		}
		return null;
	}
	

	public TreeTravelNode getFirstDescendant(String symbol, boolean includeTerminals, boolean includeNonTerminals, boolean symbolIsPrefix)
	{
		if (children==null) return null;
		
		Queue<TreeTravelNode> bfsQueue = new LinkedList<>();
		for (TreeTravelNode child : children)
		{
			boolean childIsTerminal = child.getItself().getItem().isTerminal();
			boolean include = (childIsTerminal&&includeTerminals) || ((!childIsTerminal)&&includeNonTerminals);
			if (include)
			{
				bfsQueue.add(child);
			}
		}
		while (!bfsQueue.isEmpty())
		{
			TreeTravelNode descendant = bfsQueue.remove();
			String descendantSymbol = "";
			if (descendant.getItself().getItem().isTerminal())
			{
				descendantSymbol = descendant.getItself().getItem().getTerminal().getTag();
			}
			else
			{
				descendantSymbol = descendant.getItself().getItem().getSymbol();
			}
			if ( (symbolIsPrefix&&descendantSymbol.startsWith(symbol)) || (descendantSymbol.equals(symbol)) )
			{
				return descendant;
			}
			
			ArrayList<TreeTravelNode> descendantChildren = descendant.getChildren();
			if (descendantChildren!=null)
			{
				for (TreeTravelNode descendantChild : descendant.getChildren())
				{
					boolean childIsTerminal = descendantChild.getItself().getItem().isTerminal();
					boolean include = (childIsTerminal&&includeTerminals) || ((!childIsTerminal)&&includeNonTerminals);
					if (include)
					{
						bfsQueue.add(descendantChild);
					}
				}
			}
			
		} // end while queue is not empty
		return null;
	}


	////////// PRIVATE //////////
	
	private static TreeTravelNode createFromTree(TreeNode root, int index, MutableInt terminalIndex)
	{
		final int originalTerminalIndex = terminalIndex.getValue();
		TreeTravelNode generatedRoot = new TreeTravelNode(root, index, terminalIndex.getValue());
		if (root.getChildren()!=null)
		{
			int childrenIndex=0;
			ArrayList<TreeTravelNode> children = new ArrayList<>(root.getChildren().size());
			for (TreeNode rawChild : root.getChildren())
			{
				TreeTravelNode generatedChild = createFromTree(rawChild, childrenIndex, terminalIndex);
				generatedChild.setParent(generatedRoot);
				children.add(generatedChild);
				++childrenIndex;
			}
			generatedRoot.setChildren(children);
		}
		else
		{
			generatedRoot.setChildren(null); // Not necessary. just making it explicit.
		}
		generatedRoot.setParent(null); // Again, just to make it explicit. In most cases (except the root) this will be overwritten by the caller.
		
		if (root.getItem().isTerminal())
		{
			terminalIndex.increment();
		}
		generatedRoot.setNumberOfCoveredTerminals(terminalIndex.getValue()-originalTerminalIndex);
		
		return generatedRoot;
	}




	private final TreeNode itself;
	private final int index;
	private final int terminalIndex;
	private ArrayList<TreeTravelNode> children;
	private TreeTravelNode parent;
	private int numberOfCoveredTerminals;
}
