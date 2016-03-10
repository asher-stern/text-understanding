package com.as.text_understanding.tree_util.head;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 *
 * Date: Mar 10, 2016
 * @author asher
 *
 */
public final class PrioritizedTagsContainer
{
	public static final PrioritizedTagsContainer INSTANCE = new PrioritizedTagsContainer();

	public List<String> getPrioritizedTagsForLhsTag(final String tag)
	{
		List<String> ret = mapPrioritizedTags.get(tag);
		if (null==ret) ret = Collections.emptyList();
		return ret;
	}
	
	public Direction getDirectionOfLhsTag(final String tag)
	{
		Direction ret = mapDirection.get(tag);
		if (null==ret) ret = Direction.LEFT_TO_RIGHT;
		return ret;
	}
	
	public List<SetAndDirection> getNpRules()
	{
		return npRules;
	}
	
	
	////////// IMPLEMENTATION //////////

	protected PrioritizedTagsContainer()
	{
		mapPrioritizedTags.put(
				"ADJP",
				Arrays.asList(new String[]{"NNS","QP","NN","$","ADVP","JJ","VBN","VBG","ADJP","JJR","NP","JJS","DT","FW","RBR","RBS","SBAR","RB"}
				));
		mapPrioritizedTags.put(
				"ADVP",
				Arrays.asList(new String[]{"RB","RBR","RBS","FW","ADVP","TO","CD","JJR","JJ","IN","NP","JJS","NN"}
				));
		mapPrioritizedTags.put(
				"CONJP",
				Arrays.asList(new String[]{"CC","RB","IN"}
				));
		mapPrioritizedTags.put(
				"LST",
				Arrays.asList(new String[]{"LS",":"}
				));
		mapPrioritizedTags.put(
				"NAC",
				Arrays.asList(new String[]{"NN","NNS","NNP","NNPS","NP","NAC","EX","$","CD","QP","PRP","VBG","JJ","JJS","JJR","ADJP","FW"}
				));
		mapPrioritizedTags.put(
				"PP",
				Arrays.asList(new String[]{"IN","TO","VBG","VBN","RP","FW"}
				));
		mapPrioritizedTags.put(
				"PRT",
				Arrays.asList(new String[]{"RP"}
				));
		mapPrioritizedTags.put(
				"QP",
				Arrays.asList(new String[]{"$","IN","NNS","NN","JJ","RB","DT","CD","NCD","QP","JJR","JJS"}
				));
		mapPrioritizedTags.put(
				"RRC",
				Arrays.asList(new String[]{"VP","NP","ADVP","ADJP","PP"}
				));
		mapPrioritizedTags.put(
				"S",
				Arrays.asList(new String[]{"TO","IN","VP","S","SBAR","ADJP","UCP","NP"}
				));
		mapPrioritizedTags.put(
				"SBAR",
				Arrays.asList(new String[]{"WHNP","WHPP","WHADVP","WHADJP","IN","DT","S","SQ","SINV","SBAR","FRAG"}
				));
		mapPrioritizedTags.put(
				"SBARQ",
				Arrays.asList(new String[]{"SQ","S","SINV","SBARQ","FRAG"}
				));
		mapPrioritizedTags.put(
				"SINV",
				Arrays.asList(new String[]{"VBZ","VBD","VBP","VB","MD","VP","S","SINV","ADJP","NP"}
				));
		mapPrioritizedTags.put(
				"SQ",
				Arrays.asList(new String[]{"VBZ","VBD","VBP","VB","MD","VP","SQ"}
				));
		mapPrioritizedTags.put(
				"VP",
				Arrays.asList(new String[]{"TO","VBD","VBN","MD","VBZ","VB","VBG","VBP","VP","ADJP","NN","NNS","NP"}
				));
		mapPrioritizedTags.put(
				"WHADJP",
				Arrays.asList(new String[]{"CC","WRB","JJ","ADJP"}
				));
		mapPrioritizedTags.put(
				"WHADVP",
				Arrays.asList(new String[]{"CC","WRB"}
				));
		mapPrioritizedTags.put(
				"WHNP",
				Arrays.asList(new String[]{"WDT","WP","WP$","WHADJP","WHPP","WHNP"}
				));
		mapPrioritizedTags.put(
				"WHPP",
				Arrays.asList(new String[]{"IN","TO","FW"}
				));
		
		
		
		mapDirection.put("ADJP", Direction.LEFT_TO_RIGHT);
		mapDirection.put("ADVP", Direction.RIGHT_TO_LEFT);
		mapDirection.put("CONJP", Direction.RIGHT_TO_LEFT);
		mapDirection.put("FRAG", Direction.RIGHT_TO_LEFT);
		mapDirection.put("INTJ", Direction.LEFT_TO_RIGHT);
		mapDirection.put("LST", Direction.RIGHT_TO_LEFT);
		mapDirection.put("NAC", Direction.LEFT_TO_RIGHT);
		mapDirection.put("PP", Direction.RIGHT_TO_LEFT);
		mapDirection.put("PRN", Direction.LEFT_TO_RIGHT);
		mapDirection.put("PRT", Direction.RIGHT_TO_LEFT);
		mapDirection.put("QP", Direction.LEFT_TO_RIGHT);
		mapDirection.put("RRC", Direction.RIGHT_TO_LEFT);
		mapDirection.put("S", Direction.LEFT_TO_RIGHT);
		mapDirection.put("SBAR", Direction.LEFT_TO_RIGHT);
		mapDirection.put("SBARQ", Direction.LEFT_TO_RIGHT);
		mapDirection.put("SINV", Direction.LEFT_TO_RIGHT);
		mapDirection.put("SQ", Direction.LEFT_TO_RIGHT);
		mapDirection.put("UCP", Direction.RIGHT_TO_LEFT);
		mapDirection.put("VP", Direction.LEFT_TO_RIGHT);
		mapDirection.put("WHADJP", Direction.LEFT_TO_RIGHT);
		mapDirection.put("WHADVP", Direction.RIGHT_TO_LEFT);
		mapDirection.put("WHNP", Direction.LEFT_TO_RIGHT);
		mapDirection.put("WHPP", Direction.RIGHT_TO_LEFT);
		
		
		npRules.add(new SetAndDirection(
				buildSet("NN", "NNP", "NNPS", "NNS", "NX", "POS", "JJR"),
				Direction.RIGHT_TO_LEFT
				));
		npRules.add(new SetAndDirection(
				buildSet("NP"),
				Direction.LEFT_TO_RIGHT
				));
		npRules.add(new SetAndDirection(
				buildSet("$", "ADJP", "PRN"),
				Direction.RIGHT_TO_LEFT
				));
		npRules.add(new SetAndDirection(
				buildSet("CD"),
				Direction.RIGHT_TO_LEFT
				));
		npRules.add(new SetAndDirection(
				buildSet("JJ","JJS","RB", "QP"),
				Direction.RIGHT_TO_LEFT
				));
		
		npRules = Collections.unmodifiableList(npRules);
	}
	
	@SafeVarargs
	private static <T> Set<T> buildSet(T...ts)
	{
		Set<T> ret = new LinkedHashSet<T>();
		for (T t : ts) {ret.add(t);}
		return ret;
	}
	
	private final Map<String, List<String>> mapPrioritizedTags = new LinkedHashMap<>();
	private final Map<String, Direction> mapDirection = new LinkedHashMap<>();
	private List<SetAndDirection> npRules = new LinkedList<>();
	
	
}
