package com.as.text_understanding.pasta;

import java.util.Arrays;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.uima.UIMAException;
import org.junit.Test;

import com.as.text_understanding.representation.pasta.PredicateAndArguments;
import com.as.text_understanding.representation.tree.Tree;
import com.as.text_understanding.tree_travel.TreeTravelNode;
import com.as.text_understanding.tree_util.TestTreeBuilderFromDkpro;

import static com.as.text_understanding.common.JUnitUtilities.assertListStartsWith;

/**
 * 
 *
 * Date: Mar 2, 2016
 * @author asher
 *
 */
public class TestPasta
{
	@Test
	public void testPasta() throws UIMAException
	{
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.WARN);
		
		final String sentence = "This small example shows you how to write a unit test.";
		Tree tree = TestTreeBuilderFromDkpro.buildTreeFromSentence(sentence);
		TreeTravelNode treeTravel = TreeTravelNode.createFromTree(tree.getRoot());
		Pasta pasta = new Pasta(treeTravel);
		pasta.annotate();
		List<PredicateAndArguments> pastaResult = pasta.getResult();
		System.out.println(Pasta.pasResultToString(pastaResult));
		
		assertListStartsWith(Arrays.asList(new String[]{"shows", "write"}), pastaResult, (String s)->s, (PredicateAndArguments p)->p.getPredicate().getVerbNode().getItself().getItem().getTerminal().getToken());
	}
	
}
