package com.as.text_understanding.tree_util;

import static com.as.text_understanding.common.JUnitUtilities.assertListStartsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import com.as.text_understanding.representation.tree.Tree;
import com.as.text_understanding.representation.tree.TreeItem;
import com.as.text_understanding.representation.tree.TreeNode;

import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpParser;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;

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
		Tree tree = buildTreeFromSentence(sentence);
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
	
	public static Tree buildTreeFromSentence(final String sentence) throws UIMAException
	{
		JCas cas = JCasFactory.createJCas();
		try
		{
			cas.setDocumentLanguage("en");
			cas.setDocumentText(sentence);

			SimplePipeline.runPipeline(cas, 
					AnalysisEngineFactory.createEngineDescription(OpenNlpSegmenter.class),
					AnalysisEngineFactory.createEngineDescription(OpenNlpParser.class)
					);

			TreeBuilderFromDkpro builder = new TreeBuilderFromDkpro(cas);
			Tree tree = builder.buildForSingleSentence();
			return tree;
		}
		finally
		{
			cas.release();
		}
	}
}
