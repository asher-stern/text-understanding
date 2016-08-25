package com.as.text_understanding.tree_util.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.function.Predicate;

import com.as.text_understanding.common.TextUnderstandingException;
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
public class ItemFinder
{
	public static final Set<String> CLAUSE_SYMBOLS = buildSet(new String[]{"S","SBAR","SBARQ","SINV","SQ","RRC","WHAVP","WHNP","WHPP"});
	public static final String COORDINATION_TAG = "CC";
	public static final Set<String> NON_CONTENT_POS_TAGS = buildSet(new String[]{"CC","DT","IN","LS","MD","SYM","TO"});
	
	
	/**
	 * Returns a list of concepts, where each concept is a list (a sequence) of terminal-nodes, that are assumed to express a single
	 * "concept" in the the text, like "yellow flower", "group of children", etc. (think about sentences like "a group of children
	 * traveled to the forest and found a yellow flower".
	 * <br>
	 * The returned list of concepts are concepts governed by the given node, i.e., the given node is an ancestor of these concepts,
	 * in the parse tree. In other words, all the concepts that are part of the given subtree (the given node is the subtree root).
	 * <p>
	 * Usually, only one concept is returned, when the given subtree is an argument subtree. However, in case of coordination ("and",
	 * "or", etc.) more than one concepts is returned. For example "I have a son and a daughter": the second argument of the predicate
	 * "have" is "a son and a daughter", but it contains two concepts, 1) "son", 2) "daughter".
	 * <p>
	 * Each concept is trimmed from non-content terminal nodes, like determiners.
	 * 
	 *  
	 * @param subtree
	 * @return
	 */
	public List<List<TreeTravelNode>> findItems(TreeTravelNode subtree)
	{
		List<List<TreeTravelNode>> grossConcepts = findItemsRegardslessContent(subtree, true);
		
		List<List<TreeTravelNode>> ret = new ArrayList<>(grossConcepts.size());
		for (List<TreeTravelNode> concept : grossConcepts)
		{
			ret.add(trimList(concept, terminalIsContentPredicate));
		}
		return ret;
	}
	
	/**
	 * Converts the given list of concepts, which is returned by {@link #findItems(TreeTravelNode)}, into a human-readable string.
	 * @param concepts
	 * @return
	 */
	public static String itemsToString(final List<List<TreeTravelNode>> concepts)
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
	
	
	/////////////// PRIVATE ///////////////
	
	private List<List<TreeTravelNode>> findItemsRegardslessContent(TreeTravelNode subtree, boolean forceIncludeFirst)
	{
		if (subtree.getItself().getItem().isTerminal())
		{
			return Collections.singletonList(Collections.singletonList(subtree));
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
				List<TreeTravelNode> currentItem = new LinkedList<>();
				ret.add(currentItem);
				boolean first = true;
				for (TreeTravelNode child : subtree.getChildren())
				{
					if (nodeStartsCoordination(child))
					{
						currentItem = new LinkedList<>();
						ret.add(currentItem);
					}
					
					List<List<TreeTravelNode>> ofChild = findItemsRegardslessContent(child, (first&&forceIncludeFirst) );
					boolean firstConcept = true;
					for (List<TreeTravelNode> ofChildConcept : ofChild)
					{
						if (!firstConcept)
						{
							currentItem = new LinkedList<>();
							ret.add(currentItem);
						}
						currentItem.addAll(ofChildConcept);
						firstConcept = false;
					}
					first = false;
				}
				return ret;
			}
		}
	}
	
	

	
	
	/**
	 * Trims a list by the given predicate. All the elements at the beginning of the list that are tested as <tt>false</tt>
	 * by the predicate are not included in the returned list. Similarly, all the elements at the end of the list that are
	 * tested as <tt>false</tt> are not included in the returned list.
	 * <p>
	 * If the given list is not empty, this method does not return an empty list. If all should be trimmed, then
	 * the first item will not be trimmed.
	 * 
	 * @param originalList a list
	 * @param predicateToSurvive a predicate over the elements of the list, such that all elements at the beginning and the end of 
	 * the list that are tested as <tt>false</tt> by the predicate are not included in the returned list.
	 * 
	 * @return A list similar to the original list, but trimmed at its beginning and its end.
	 */
	private static <T> List<T> trimList(List<T> originalList, Predicate<T> predicateToSurvive)
	{
		if (null==originalList) return null;
		if (originalList.isEmpty()) return originalList;
				
		int endExclusive = originalList.size();
		ListIterator<T> backwardIterator = originalList.listIterator(originalList.size());
		while (backwardIterator.hasPrevious())
		{
			T item = backwardIterator.previous();
			if (predicateToSurvive.test(item))
			{
				break;
			}
			--endExclusive;
		}
		
		int begin = 0;
		ListIterator<T> forwardIterator = originalList.listIterator();
		while (forwardIterator.hasNext())
		{
			T item = forwardIterator.next();
			if (predicateToSurvive.test(item))
			{
				break;
			}
			++begin;
		}
		
		if (begin>=endExclusive)
		{
			return Collections.singletonList(originalList.get(0)); // don't return an empty list.
		}
		else
		{
			List<T> ret = new ArrayList<>(endExclusive-begin);
			ListIterator<T> iterator = originalList.listIterator();
			int index = 0;
			while (iterator.hasNext())
			{
				if (index>=endExclusive)
				{
					break;
				}
				T item = iterator.next();
				if (index>=begin)
				{
					ret.add(item);
				}
				++index;
			}
			return ret;
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
	
	private static class TerminalIsContentPredicate implements Predicate<TreeTravelNode>
	{
		@Override
		public boolean test(TreeTravelNode t)
		{
			TreeItem item = t.getItself().getItem();
			if (!item.isTerminal()) throw new TextUnderstandingException("Encountered a non-terminal node in concept.");
			return terminalIsContent(item.getTerminal());
		}
	}
	
	private static final TerminalIsContentPredicate terminalIsContentPredicate = new TerminalIsContentPredicate();
}
