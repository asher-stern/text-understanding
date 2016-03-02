package com.as.text_understanding.tree_util;

import org.junit.Test;

import com.as.text_understanding.representation.tree.Tree;
import com.as.text_understanding.representation.tree.TreeItem;
import com.as.text_understanding.representation.tree.TreeNode;

import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpParser;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.apache.uima.UIMAException;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;

/**
 * 
 * 
 * Date: March 2, 2016
 * @author asher
 *
 */
public class TestTreeBuilderFromDkpro
{
	@Test
	public void testTreeBuilder() throws UIMAException
	{
		final String sentence = "This small example shows you how to write a unit test.";
		JCas cas = JCasFactory.createJCas();
		cas.setDocumentLanguage("en");
		cas.setDocumentText(sentence);
		
		SimplePipeline.runPipeline(cas, 
				AnalysisEngineFactory.createEngineDescription(OpenNlpSegmenter.class),
				AnalysisEngineFactory.createEngineDescription(OpenNlpParser.class)
				);
		
		TreeBuilderFromDkpro builder = new TreeBuilderFromDkpro(cas);
		builder.build();
		Tree tree = builder.getTree();
		String treeString = TreeUtilities.treeToString(tree);
		System.out.println(treeString);
		
		assertFalse("Tree root is a terminal", tree.getRoot().getItem().isTerminal());
		assertEquals("Tree root is not \"S\"" ,"S", tree.getRoot().getItem().getSymbol());
		List<TreeNode> topChildren = tree.getRoot().getChildren();
		
		List<TreeNode> expected = new ArrayList<>(1+1);
		expected.add(new TreeNode(new TreeItem("NP"),null));
		expected.add(new TreeNode(new TreeItem("VP"),null));
		assertListStartsWith(expected, topChildren, (TreeNode t)->t.getItem().getSymbol());
	}
	
	private static <T,R> void assertListStartsWith(List<T> expected, List<T> actual, Function<T, R> function)
	{
		Iterator<T> expectedIterator = expected.iterator();
		Iterator<T> actualIterator = actual.iterator();
		while (expectedIterator.hasNext())
		{
			assertTrue("Actual to short", actualIterator.hasNext());
			if (actualIterator.hasNext())
			{
				T expectedItem = expectedIterator.next();
				T actualItem = actualIterator.next();
				assertEquals("Item mismatch",function.apply(expectedItem), function.apply(actualItem));
			}
		}
	}
	
}
