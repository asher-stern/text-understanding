package com.as.text_understanding.tree_util.head;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.lang3.mutable.MutableInt;

import com.as.text_understanding.TextUnderstandingException;
import com.as.text_understanding.representation.tree.TreeItem;
import com.as.text_understanding.representation.tree.TreeNode;

/**
 * TODO Change the implementation to return the nodes themselves, rather than indexes.
 * TODO List.get() is used by this class.
 *  
 *
 * Date: Mar 10, 2016
 * @author asher
 *
 */
public class HeadFinder
{
	public static int findHead(TreeNode treeNode)
	{
		return templatedFindHead(treeItemTagExtractor.apply(treeNode.getItem()), treeNode.getChildren(), treeNodeTagExtractor);
	}

	public static int findHead(String lhsTag, List<TreeItem> items)
	{
		return templatedFindHead(lhsTag, items, treeItemTagExtractor);
	}
	
	public static TreeNode findTerminalHead(TreeNode treeNode)
	{
		if (treeNode.getItem().isTerminal()) {return treeNode;}
		int childHeadIndex = findHead(treeNode);
		List<TreeNode> children = treeNode.getChildren();
		if (childHeadIndex >= children.size()) {throw new TextUnderstandingException("Wrong head index has been returned from findHead()");}
		return findTerminalHead(children.get(childHeadIndex));
	}

	
	private static <T> int scanLeftToRight(List<T> items, List<String> prioritizedTags, Function<T, String> tagExtractor)
	{
		int size = items.size();
		if (size==0) throw new TextUnderstandingException("empty vector of items");
		for (String tag : prioritizedTags)
		{
			Iterator<T> itemIterator = items.iterator();
			for (int index=0; index<size; ++index)
			{
				T item = itemIterator.next();
				String itemString = tagExtractor.apply(item);
				if (tag.equals(itemString))
				{
					return index;
				}
			}
		}
		return 0;
	}

	private static <T> int scanRightToLeft(List<T> items, List<String> prioritizedTags, Function<T,String> tagExtractor)
	{
		final int size = items.size();
		if (size==0) throw new TextUnderstandingException("empty vector of items");
		for (String tag : prioritizedTags)
		{
			ListIterator<T> itemIterator = items.listIterator(size);
			for (int index=size-1; index>=0; --index)
			{
				T item = itemIterator.previous();
				String itemString = tagExtractor.apply(item);
				if (tag.equals(itemString))
				{
					return index;
				}
			}
		}
		return size-1;
	}
	
	
	
	private static <T> boolean scanOptionalSetLeftToRight(List<T> items, Set<String> tags, Function<T, String> tagExtractor, MutableInt index)
	{
		final int size = items.size();
		Iterator<T> itemIterator = items.iterator();
		for (int i=0; i<size; ++i)
		{
			T item = itemIterator.next();
			String itemTag = tagExtractor.apply(item);
			if (tags.contains(itemTag))
			{
				index.setValue(i);
				return true;
			}
		}
		return false;
	}


	
	private static <T> boolean scanOptionalSetRightToLeft(List<T> items, Set<String> tags, Function<T, String> tagExtractor, MutableInt index)
	{
		final int size = items.size();
		ListIterator<T> itemIterator = items.listIterator(size);
		for (int i=size-1; i>=0; --i)
		{
			T item = itemIterator.previous();
			String itemTag = tagExtractor.apply(item);
			if (tags.contains(itemTag))
			{
				index.setValue(i);
				return true;
			}
		}
		return false;
	}
	
	
	
	private static <T> int findHeadOfNP(String lhsTag, List<T> items, Function<T, String> tagExtractor)
	{
		final int size = items.size();
		int headIndex = 0;
		final String lastWordTag = tagExtractor.apply(items.get(size-1));
		if (lastWordTag.equals("POS"))
		{
			headIndex = size-1;
		}
		else
		{
			final List<SetAndDirection> npRules = PrioritizedTagsContainer.INSTANCE.getNpRules();
			boolean detected = false;
			for (SetAndDirection rule : npRules)
			{
				if (rule.getDirection()==Direction.LEFT_TO_RIGHT)
				{
					MutableInt mHeadIndex = new MutableInt(headIndex);
					detected = scanOptionalSetLeftToRight(items, rule.getSet(), tagExtractor, mHeadIndex);
					headIndex = mHeadIndex.intValue();
				}
				else
				{
					MutableInt mHeadIndex = new MutableInt(headIndex);
					detected = scanOptionalSetRightToLeft(items, rule.getSet(), tagExtractor, mHeadIndex);
					headIndex = mHeadIndex.intValue();
				}
				if (detected)
				{
					break;
				}
			}
			if (!detected)
			{
				headIndex = size-1;
			}
		}
		
		if (headIndex>=2) // indexes start from 0
		{
			final String oneBeforeTag = tagExtractor.apply(items.get(headIndex-1));
			if (oneBeforeTag.equals("CC"))
			{
				headIndex=headIndex-2;
			}
		}
		return headIndex;
	}
	
	private static <T> int templatedFindHead(String lhsTag, List<T> items, Function<T, String> tagExtractor)
	{
		if (lhsTag.equals("NP"))
		{
			return findHeadOfNP(lhsTag, items, tagExtractor);
		}
		else // not NP
		{
			List<String> prioritizedTags = PrioritizedTagsContainer.INSTANCE.getPrioritizedTagsForLhsTag(lhsTag);
			switch(PrioritizedTagsContainer.INSTANCE.getDirectionOfLhsTag(lhsTag))
			{
			case LEFT_TO_RIGHT:
				return scanLeftToRight(items, prioritizedTags, tagExtractor);
			case RIGHT_TO_LEFT:
				return scanRightToLeft(items, prioritizedTags, tagExtractor);
			default:
				throw new TextUnderstandingException("bug");
			}
		}
	}
	
	private static class TreeItemTagExtractor implements Function<TreeItem, String>
	{
		@Override
		public String apply(TreeItem t)
		{
			if (null==t) return "";
			String ret = null;
			if (t.isTerminal())
			{
				ret = t.getTerminal().getTag();
			}
			else
			{
				ret = t.getSymbol();
			}
			if (null==ret) ret = "";
			return ret;
		}
	}
	
	private static class TreeNodeTagExtractor implements Function<TreeNode, String>
	{
		@Override
		public String apply(TreeNode t)
		{
			if (null==t) return null;
			return treeItemTagExtractor.apply(t.getItem());
		}
		
	}
	
	private static final TreeItemTagExtractor treeItemTagExtractor = new TreeItemTagExtractor();
	private static final TreeNodeTagExtractor treeNodeTagExtractor = new TreeNodeTagExtractor();
}
