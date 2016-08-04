package com.as.text_understanding.pasta;

import static com.as.text_understanding.common.TextUnderstandingUtilities.each;
import static com.as.text_understanding.tree_util.TreeUtilities.treeToYield;
import static com.as.text_understanding.tree_util.TreeUtilities.yieldToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.as.text_understanding.common.TextUnderstandingException;
import com.as.text_understanding.representation.pasta.Argument;
import com.as.text_understanding.representation.pasta.ArgumentType;
import com.as.text_understanding.representation.pasta.Predicate;
import com.as.text_understanding.representation.pasta.PredicateAndArguments;
import com.as.text_understanding.representation.tree.TreeItem;
import com.as.text_understanding.representation.tree.TreeNode;
import com.as.text_understanding.tree_travel.TreeTravelNode;
import com.as.text_understanding.tree_util.TreeUtilities;
import com.as.text_understanding.tree_util.head.HeadFinder;
import com.as.text_understanding.tree_util.item.ItemFinder;

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

	public Pasta(TreeTravelNode root)
	{
		super();
		this.root = root;
	}

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
	
	public List<PredicateAndArguments> getResult()
	{
		return result;
	}




	public static String pasResultToString(final List<PredicateAndArguments> pasResult)
	{
		return pasResultToString(pasResult, false, false);
	}
	
	public static String pasResultToString(final List<PredicateAndArguments> pasResult, boolean printHead, boolean printItem)
	{
		StringBuilder sb = new StringBuilder();
		for (final PredicateAndArguments predicateAndArguments : pasResult)
		{
			if (!isTerminal(predicateAndArguments.getPredicate().getVerbNode())) {throw new TextUnderstandingException("verb node is a non-terminal");}
			final String verbWord = predicateAndArguments.getPredicate().getVerbNode().getItself().getItem().getTerminal().getToken();
			sb.append(verbWord).append("\n");
			for (final Argument argument : predicateAndArguments.getArguments())
			{
				sb.append("\t").append(argument.isClause()?" (c)":"").append(" (").append(argument.getType().name()).append(") ");
				if (argument.getPreposition()!=null)
				{
					sb.append("<").append(printPreposition(argument.getPreposition())).append("> ");
				}
				if (printHead)
				{
					TreeNode headNode = HeadFinder.findTerminalHead(argument.getSubtree().getItself());
					final String head = headNode.getItem().getTerminal().getToken();
					sb.append(head).append(": ");
				}
				if (printItem && (!argument.isClause()))
				{
					ItemFinder itemFinder = new ItemFinder();
					sb.append(ItemFinder.itemsToString(itemFinder.findItems(argument.getSubtree()))).append(": ");
				}
				sb.append(yieldToString(treeToYield(argument.getSubtree().getItself()))).append("\n");
			}
		}
		return sb.toString();
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
		TreeTravelNode ret = vpNode.getLastPredecessorSibling("NP");
		if (null==ret)
		{
			ret = vpNode.getFirstSibling("NP");
		}
		if (null==ret)
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
	
	
	
	private void handleRRC(TreeTravelNode node)
	{
		TreeTravelNode childVP = null;
		ArrayList<TreeTravelNode> children = node.getChildren();
		for (TreeTravelNode child : children)
		{
			TreeItem childItem = child.getItself().getItem();
			if (!isTerminal(child))
			{
				if (childItem.getSymbol().equals("VP"))
				{
					childVP = child;
					break;
				}
			}
		}
		
		if (childVP != null)
		{
			TreeTravelNode sibling = node.getImmediateNonTerminalPredecessorSibling();
			TreeTravelNode argumentNode = findArgumentInNodeItself(sibling);
			if (argumentNode!=null)
			{
//				addArgumentToDetectedArguments(childVP, new Argument(ArgumentType.SUBJECT, false, argumentNode));
				addArgumentToDetectedArguments(childVP, buildArgument(sibling, ArgumentType.SUBJECT) ); // buildArgument on the sibling will find again argumentNode, which is not null.
			}
		}
	}
	
	
	
	private void handleSBAR(TreeTravelNode node)
	{
		boolean containsWH_P = false;
		for (TreeTravelNode child : node.getChildren())
		{
			if (!isTerminal(child))
			{
				final String childSymbol = child.getItself().getItem().getSymbol();
				if ( childSymbol.startsWith("WH") && childSymbol.endsWith("P") )
				{
					containsWH_P=true;
					break;
				}
			}
		}
		
		if (containsWH_P)
		{
			TreeTravelNode vpDescendant = node.getFirstDescendant("VP", false, true, false);
			if (vpDescendant!=null)
			{
				TreeTravelNode subjectNode = null;
				subjectNode = node.getLastPredecessorSibling("NP");
				if (subjectNode==null)
				{
					subjectNode = node.getLastPredecessorSibling("PP");
				}
				if (subjectNode!=null)
				{
//					subjectNode = findArgumentInNodeItself(subjectNode);
//					if (subjectNode==null) { throw new TextUnderstandingException("findArgumentInNodeItself returned nullptr unexpectedly."); }
//					addArgumentToDetectedArguments(vpDescendant, new Argument(ArgumentType.SUBJECT, false, subjectNode) );
					addArgumentToDetectedArguments(vpDescendant, buildArgument(subjectNode, ArgumentType.SUBJECT) );
				}
			}
		}
	}
	
	
	private LinkedList<Argument> mergeArgumentLists(final List<Argument> highPriorityList, final List<Argument> lowPriorityList)
	{
		LinkedList<Argument> ret = new LinkedList<>();
		if (highPriorityList!=null)
		{
			ret.addAll(highPriorityList);
		}
		LinkedHashSet<TreeTravelNode> highPriorityNodes = new LinkedHashSet<>();
		if (highPriorityList!=null)
		{
			for (Argument argument : highPriorityList)
			{
				highPriorityNodes.add(argument.getSubtree());
			}
		}

		boolean subjectAlreadyExists=false;
		if (highPriorityList!=null)
		{
			for (Argument argument : highPriorityList)
			{
				if (argument.getType()==ArgumentType.SUBJECT)
				{
					subjectAlreadyExists=true;
					break;
				}
			}
		}

		if (lowPriorityList!=null)
		{
			for (final Argument argument : lowPriorityList)
			{
				if (!highPriorityNodes.contains(argument.getSubtree())) // it does not exist in the other list.
				{
					if (subjectAlreadyExists && (argument.getType()==ArgumentType.SUBJECT) )
					{
						ret.add(new Argument(ArgumentType.OBJECT, argument.isClause(), argument.getSubtree()));
					}
					else
					{
						ret.add(argument);
					}
				}
			}
		}

		return ret;
	}

	
	private Argument buildArgument(final TreeTravelNode argumentRoot, ArgumentType typeIfNP_PP_S)
	{
		TreeTravelNode argumentNode = argumentRoot;
		List<TreeTravelNode> preposition = null;
		if (argumentRoot.getItself().getItem().getSymbol().equals("PP"))
		{
			argumentNode = findArgumentInNodeItself(argumentRoot);
			if (argumentNode==null) {throw new TextUnderstandingException("findArgumentInNodeItself returned nullptr unexpectedly.");}
			preposition = findPreposition(argumentRoot);
		}
		ArgumentType type = typeIfNP_PP_S;
		final String argumentSymbol = argumentRoot.getItself().getItem().getSymbol();
		// argumentSymbol.size() should not be 0, but, to be on the safe side I add this check
		if ( (argumentSymbol.length()==0) || ( (!argumentSymbol.equals("NP")) && (!argumentSymbol.equals("PP")) && (!argumentSymbol.startsWith("S")) ) )
		{
			type = ArgumentType.MODIFIER;
		}
		boolean clause = (argumentSymbol.length()>0) && (argumentSymbol.startsWith("S"));
		return new Argument(type, clause, preposition, argumentNode);
	}
	
	private List<TreeTravelNode> findPreposition(final TreeTravelNode prepositionPhrase)
	{
		ArrayList<TreeTravelNode> children = prepositionPhrase.getChildren();
		List<TreeTravelNode> ret = new LinkedList<>();
		int index=0;
		for (TreeTravelNode child : children)
		{
			if ( (index+1)==children.size() )
			{
				break;
			}
			if (!child.getItself().getItem().isTerminal())
			{
				if (child.getItself().getItem().getSymbol().equals("NP"))
				{
					break;
				}
			}
			ret.add(child);
			++index;
		}
		return ret;
	}
	
	
	
	private void findPas(TreeTravelNode node)
	{
		LinkedList<Argument> arguments = new LinkedList<>();
		Set<TreeTravelNode> actualVPs = findActualVerbsVP(node);

		TreeTravelNode subject = findSubject(node);
		if (subject!=null)
		{
//			TreeTravelNode argumentNode = subject;
//			if (subject.getItself().getItem().getSymbol().equals("PP"))
//			{
//				argumentNode = findArgumentInNodeItself(subject);
//				if (argumentNode==null) {throw new TextUnderstandingException("findArgumentInNodeItself returned nullptr unexpectedly.");}
//			}
//			arguments.add(new Argument(ArgumentType.SUBJECT, false, argumentNode));
			arguments.add(buildArgument(subject, ArgumentType.SUBJECT));
		}

		List<TreeTravelNode> siblingArguments = findSiblingArguments(node);
		for (TreeTravelNode siblingArgument : siblingArguments)
		{
			if ( (subject==null) || (subject!=siblingArgument) ) // = if this is not the subject (that was added earlier)
			{
				arguments.add(buildArgument(siblingArgument, ArgumentType.OBJECT));
			}
		}

		for (TreeTravelNode actualVP : actualVPs)
		{
			if (!alreadyHandled.contains(actualVP))
			{
				TreeTravelNode verbNode = actualVP.getFirstDescendant("V", true, false, true);
				if (verbNode!=null)
				{
					LinkedList<Argument> actualArguments = new LinkedList<>();
					actualArguments.addAll(arguments);

					List<TreeTravelNode> inVpArguments = findInVPArguments(actualVP);
					for (TreeTravelNode inVpArgumentNode : inVpArguments)
					{
						actualArguments.add(buildArgument(inVpArgumentNode, ArgumentType.OBJECT));
					}

					if (mapDetectedArguments.containsKey(actualVP))
					{
						actualArguments = mergeArgumentLists(mapDetectedArguments.get(node), actualArguments);
					}
					if (mapDetectedArguments.containsKey(node))
					{
						actualArguments = mergeArgumentLists(mapDetectedArguments.get(node), actualArguments);
					}

					Predicate predicate = new Predicate(actualVP, verbNode);
					ArrayList<Argument> argumentsVector = new ArrayList<>(actualArguments.size());
					argumentsVector.addAll(actualArguments);
					PredicateAndArguments predicateAndArguments = new PredicateAndArguments(predicate, argumentsVector);
					result.add(predicateAndArguments);
				}
				alreadyHandled.add(actualVP);
			}
		}

		alreadyHandled.add(node);
	}
	

	
	
	
	
	private static boolean isTerminal(TreeTravelNode node)
	{
		return node.getItself().getItem().isTerminal();
	}
	
	private static final String printPreposition(TreeTravelNode prepositionNode)
	{
		return TreeUtilities.yieldToString(TreeUtilities.treeToYield(prepositionNode.getItself()));
	}
	
	private static final String printPreposition(List<TreeTravelNode> prepositionNodes)
	{
		StringBuilder sb = new StringBuilder();
		boolean firstIteration = true;
		for (TreeTravelNode node : prepositionNodes)
		{
			if (firstIteration){firstIteration=false;} else {sb.append(" ");}
			sb.append(printPreposition(node));
		}
		return sb.toString();
	}
//	private static final String printPreposition(TreeTravelNode prepositionNode)
//	{
//		System.out.println(prepositionNode.getIndex());
//		System.out.println(prepositionNode.getItself().getItem().getSymbol());
//		ArrayList<TreeTravelNode> children = prepositionNode.getChildren();
//		StringBuilder sb = new StringBuilder();
//		boolean firstIteration = true;
//		int index = 0;
//		for (TreeTravelNode child : children)
//		{
//			if ( (index+1) >= children.size() )
//			{
//				break;
//			}
//			TreeItem childItem = child.getItself().getItem();
//			if (!childItem.isTerminal())
//			{
//				if (childItem.getSymbol().equals("NP"))
//					break;
//			}
//			if (firstIteration) {firstIteration=false;}
//			else {sb.append(" ");}
//			sb.append(TreeUtilities.yieldToString(TreeUtilities.treeToYield(child.getItself())));
//			
//			++index;
//		}
//		
//		return sb.toString();
//	}
	

	
	private final TreeTravelNode root;
	
	private Set<TreeTravelNode> alreadyHandled;
	private Map<TreeTravelNode, List<Argument>> mapDetectedArguments; // map from a VP to its arguments that were detected earlier.
	private List<PredicateAndArguments> result;
	private Queue<TreeTravelNode> bfsQueue;
	
	@SuppressWarnings("unused")
	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Pasta.class);
}
