package com.as.text_understanding.tree_util.concept;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.as.text_understanding.TextUnderstandingException;
import com.as.text_understanding.representation.tree.Terminal;
import com.as.text_understanding.representation.tree.TreeItem;
import com.as.text_understanding.tree_travel.TreeTravelNode;

/**
 * 
 *
 * Date: Mar 12, 2016
 * @author asher
 *
 */
public class ConceptFinder
{
	public static final Set<String> CLAUSE_SYMBOLS = buildSet(new String[]{"S","SBAR","SBARQ","SINV","SQ","RRC","WHAVP","WHNP","WHPP"});
	public static final String COORDINATION_TAG = "CC";
	public static final Set<String> NON_CONTENT_POS_TAGS = buildSet(new String[]{"CC","DT","IN","LS","MD","SYM","TO"});
	
	public List<List<TreeTravelNode>> findConcepts(TreeTravelNode subtree)
	{
		 return findConcepts(subtree, true);
	}
	
	public static String conceptsToString(final List<List<TreeTravelNode>> concepts)
	{
		StringBuilder sb = new StringBuilder();
		boolean multipleConcepts = (concepts.size()>1);
		boolean multipleConceptsFirstIteration = true;
		for (List<TreeTravelNode> concept : concepts)
		{
			if (multipleConcepts)
			{
				if (multipleConceptsFirstIteration) {multipleConceptsFirstIteration=false;}
				else {sb.append(", ");}
				sb.append("{");
			}
			boolean firstIteration = true;
			for (TreeTravelNode node : concept)
			{
				if (!node.getItself().getItem().isTerminal()) throw new TextUnderstandingException("Non terminal node in concept.");
				if (firstIteration) {firstIteration=false;}
				else {sb.append(" ");}
				sb.append(node.getItself().getItem().getTerminal().getToken());
			}
			if (multipleConcepts) {sb.append("}");}
		}
		return sb.toString();
	}
	
	private List<List<TreeTravelNode>> findConcepts(TreeTravelNode subtree, boolean forceIncludeFirst)
	{
		if (subtree.getItself().getItem().isTerminal())
		{
			boolean include = terminalIsContent(subtree.getItself().getItem().getTerminal());
			if (include)
			{
				return Collections.singletonList(Collections.singletonList(subtree));
			}
			else
			{
				return Collections.emptyList();
			}
		}
		else
		{
			final String symbol = subtree.getItself().getItem().getSymbol();
			if ( (!forceIncludeFirst) && CLAUSE_SYMBOLS.contains(symbol))
			{
				return Collections.emptyList();
			}
			else
			{
				List<List<TreeTravelNode>> ret = new LinkedList<>();
				List<TreeTravelNode> currentConcept = new LinkedList<>();
				ret.add(currentConcept);
				boolean first = true;
				for (TreeTravelNode child : subtree.getChildren())
				{
					if (nodeStartsCoordination(child))
					{
						currentConcept = new LinkedList<>();
						ret.add(currentConcept);
					}
					
					List<List<TreeTravelNode>> ofChild = findConcepts(child, (first&&forceIncludeFirst) );
					boolean firstConcept = true;
					for (List<TreeTravelNode> ofChildConcept : ofChild)
					{
						if (!firstConcept)
						{
							currentConcept = new LinkedList<>();
							ret.add(currentConcept);
						}
						currentConcept.addAll(ofChildConcept);
						firstConcept = false;
					}
					first = false;
				}
				return ret;
			}
		}
	}
	
	
	private static boolean terminalIsContent(Terminal terminal)
	{
		final String token = terminal.getToken();
		boolean letterOrDigitDetected = false;
		for (char c : token.toCharArray())
		{
			if (Character.isLetterOrDigit(c))
			{
				letterOrDigitDetected = true;
				break;
			}
		}
		
		if (letterOrDigitDetected)
		{
			if (!NON_CONTENT_POS_TAGS.contains(terminal.getTag()))
			{
				return true;
			}
		}
		
		return false;
	}
	
	private static boolean nodeStartsCoordination(TreeTravelNode node)
	{
		TreeItem item = node.getItself().getItem();
		if (item.isTerminal())
		{
			if (COORDINATION_TAG.equals(item.getTerminal().getTag()))
			{
				return true;
			}
		}
		return false;
	}
	
	@SafeVarargs
	private static <T> Set<T> buildSet(T...ts)
	{
		Set<T> ret = new LinkedHashSet<>();
		for (T t : ts) {ret.add(t);}
		return ret;
	}
}
