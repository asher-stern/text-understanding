package com.as.text_understanding.representation.pasta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.as.text_understanding.representation.tree.TreeItem;
import com.as.text_understanding.tree_travel.TreeTravelNode;

/**
 * 
 *
 * Date: Feb 24, 2016
 * @author Asher Stern
 *
 */
public class Pasta
{
	public static Set<String> ARGUMENT_PHRASE_SYMBOLS = new LinkedHashSet<>(Arrays.asList(new String[]{"NP", "PP", "ADJP", "ADVP"}));
	
	public void annotate()
	{
		alreadyHandled = new LinkedHashSet<>();
		mapDetectedArguments = new LinkedHashMap<>();
		result = new LinkedList<>();
		
		bfsQueue = new LinkedList<>();
		bfsQueue.add(root);
		while (!bfsQueue.isEmpty())
		{
			TreeTravelNode next = bfsQueue.remove();
			process(next);
		}
	}
	
	private void process(TreeTravelNode node)
	{
		if (!isTerminal(node))
		{
			final String symbol = node.getItself().getItem().getSymbol();
			if (symbol.equals("VP"))
			{
				if (!alreadyHandled.contains(node))
				{
					findPas(node);
				}
			}
			else if (symbol.equals("SBAR"))
			{
				handleSBAR(node);
			}
			else if (symbol.equals("RRC"))
			{
				handleRRC(node);
			}
			
			if (node.getChildren()!=null)
			{
				for (TreeTravelNode child : node.getChildren())
				{
					bfsQueue.add(child);
				}
			}
			
		}
	}
	
	// Assumes the input is a VP node.
	private Set<TreeTravelNode> findActualVerbsVP(TreeTravelNode node)
	{
		ArrayList<TreeTravelNode> nonTerminalChildren = node.getNonTerminalChildren();
		boolean actualVPsInChildren = true;
		boolean vpDetected = false;
		
		for (TreeTravelNode child : each(nonTerminalChildren))
		{
			String symbol = child.getItself().getItem().getSymbol();
			if (symbol.equals("VP"))
			{
				vpDetected=true;
			}
			if ( (!symbol.equals("VP")) && (!symbol.equals("CC")) )
			{
				actualVPsInChildren = false;
			}
		}
		if (!vpDetected)
		{
			actualVPsInChildren = false;
		}

		if (actualVPsInChildren)
		{
			Set<TreeTravelNode> ret = new LinkedHashSet<>();
			for (TreeTravelNode child : each(nonTerminalChildren))
			{
				String symbol = child.getItself().getItem().getSymbol();
				if (symbol.equals("VP"))
				{
					ret.addAll(findActualVerbsVP(child));
				}
			}
			return ret;
		}
		else
		{
			Set<TreeTravelNode> ret = new LinkedHashSet<>();
			ret.add(node);
			return ret;
		}
	}
	
	// TODO what about "S", "SINV", etc. ?
	TreeTravelNode findSubject(TreeTravelNode vpNode)
	{
		TreeTravelNode ret = vpNode.getFirstSibling("NP");
		if (ret==null)
		{
			ret = vpNode.getFirstSibling("PP");
		}
		return ret;
	}

	
	// This includes subject!
	private List<TreeTravelNode> findSiblingArguments(TreeTravelNode vpNode)
	{
		List<TreeTravelNode> ret = new LinkedList<>();
		ArrayList<TreeTravelNode> siblings = vpNode.getAllSiblings();
		for (TreeTravelNode sibling : each(siblings))
		{
			TreeItem siblingItem = sibling.getItself().getItem();
			if (!siblingItem.isTerminal())
			{
				String symbol = siblingItem.getSymbol();
				if ( ARGUMENT_PHRASE_SYMBOLS.contains(symbol) || symbol.startsWith("S") )
				{
					ret.add(sibling);
				}
			}
		}
		return ret;
	}

	
	private List<TreeTravelNode> findInVPArguments(TreeTravelNode vpNode)
	{
		List<TreeTravelNode> ret = new LinkedList<>();
		ArrayList<TreeTravelNode> children = vpNode.getChildren();
		for (TreeTravelNode child : each(children))
		{
			if (!isTerminal(child))
			{
				String symbol = child.getItself().getItem().getSymbol();
				if (symbol.length()>0)
				{
					if ( ARGUMENT_PHRASE_SYMBOLS.contains(symbol) || symbol.startsWith("S") )
					{
						ret.add(child);
					}
				}
			}
		}
		return ret;
	}

	// The given node is some node that might be an argument:
	// if it is NP - it is an argument,
	// if it is PP - it's first NP child is an argument,
	// otherwise, it is not an argument.
	private TreeTravelNode findArgumentInNodeItself(TreeTravelNode node)
	{
		if (node!=null)
		{
			TreeItem nodeItem = node.getItself().getItem();
			String symbol = nodeItem.getSymbol();
			if (symbol.equals("PP"))
			{
				ArrayList<TreeTravelNode> ppChildren = node.getNonTerminalChildren();
				for (TreeTravelNode ppChild : each(ppChildren))
				{
					String childSymbol = ppChild.getItself().getItem().getSymbol();
					if ( childSymbol.equals("NP" ) || childSymbol.startsWith("S") )
					{
						return ppChild;
					}
				}
			}
		}
		return node;
	}

	
	
	private void addArgumentToDetectedArguments(TreeTravelNode vpNode, final Argument argument)
	{
		// const unsigned int uniqueID = argument.getRoot()->getUniqueId();
		if (mapDetectedArguments.containsKey(vpNode))
		{
			List<Argument> ofNode = mapDetectedArguments.get(vpNode);
			boolean alreadyExists = false;
			for (Argument existingArgument : each(ofNode))
			{
				if (existingArgument.getSubtree()==argument.getSubtree())
				{
					alreadyExists=true;
					break;
				}
			}
			if (!alreadyExists)
			{
				mapDetectedArguments.get(vpNode).add(argument);
			}
		}
		else
		{
			List<Argument> _list = new LinkedList<>();
			_list.add(argument);
			mapDetectedArguments.put(vpNode, _list);
		}
	}
	
	

	
	
	
	
	private static boolean isTerminal(TreeTravelNode node)
	{
		return node.getItself().getItem().isTerminal();
	}
	
	private static <T> Iterable<T> each(Iterable<T> originalIterable)
	{
		if (originalIterable==null)
		{
			return Collections.emptyList();
		}
		else
		{
			return originalIterable;
		}
	}

	
	private final TreeTravelNode root;
	
	private Set<TreeTravelNode> alreadyHandled;
	private Map<TreeTravelNode, List<Argument>> mapDetectedArguments; // map from a VP to its arguments that were detected earlier.
	private List<PredicateAndArguments> result;
	private Queue<TreeTravelNode> bfsQueue;
}
