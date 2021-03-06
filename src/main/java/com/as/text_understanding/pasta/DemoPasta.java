package com.as.text_understanding.pasta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;

import com.as.text_understanding.common.LogInit;
import com.as.text_understanding.representation.pasta.PredicateAndArguments;
import com.as.text_understanding.representation.tree.Tree;
import com.as.text_understanding.tree_travel.TreeTravelNode;
import com.as.text_understanding.tree_util.TreeBuilderFromDkpro;
import com.as.text_understanding.tree_util.TreeUtilities;

import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpParser;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;

/**
 * An executable Java program that runs PASTA on sentences given from the standard input.
 * The program parses the sentences (using DKPro UIMA annotators) and then runs PASTA to extract predicate-argument structures from
 * the syntactic analysis (the parsing output). The PASTA result is printed in a human readable form.
 * 
 * <p>
 * Date: Mar 11, 2016
 * @author Asher Stern
 *
 */
public class DemoPasta
{
	@SuppressWarnings("unchecked")
	public static final Class<? extends AnalysisComponent>[] PRECONDITION_ANNOTATORS = (Class<? extends AnalysisComponent>[]) new Class<?>[]
	{
		OpenNlpSegmenter.class,
		OpenNlpParser.class
	};
	
	public static void main(String[] args)
	{
		try
		{
			LogInit.init();
			go();
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}

	}
	
	public static void go() throws UIMAException, IOException
	{
		AnalysisEngine[] engines = new AnalysisEngine[PRECONDITION_ANNOTATORS.length];
		for (int clsIndex=0; clsIndex<PRECONDITION_ANNOTATORS.length; ++clsIndex)
		{
			engines[clsIndex] = AnalysisEngineFactory.createEngine(PRECONDITION_ANNOTATORS[clsIndex]);
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		JCas cas = JCasFactory.createJCas();
		try
		{
			while (true)
			{
				System.out.println("Please enter a sentence");
				String sentence = reader.readLine().trim();
				if ("exit".equals(sentence)) break;
				cas.setDocumentLanguage("en");
				cas.setDocumentText(sentence);
				System.out.println("Processing pipeline...");
				SimplePipeline.runPipeline(cas, engines);
				TreeBuilderFromDkpro builder = new TreeBuilderFromDkpro(cas);
				Tree tree = builder.buildForSingleSentence();
				TreeTravelNode treeTravel = TreeTravelNode.createFromTree(tree.getRoot());
				System.out.println("Running Pasta...");
				Pasta pasta = new Pasta(treeTravel);
				pasta.annotate();
				List<PredicateAndArguments> pasResult = pasta.getResult();
				System.out.println();
				System.out.println(TreeUtilities.treeToElegantString(tree.getRoot(), true));
//				System.out.println(TreeUtilities.treeToString(tree));
				System.out.println(Pasta.pasResultToString(pasResult, false, true));
				
				System.out.println();
				cas.reset();
			}
		}
		finally
		{
			cas.release();
		}
	}

}
