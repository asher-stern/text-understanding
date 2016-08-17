package com.as.text_understanding.uima_annotators.pasta;

import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.tools.docanalyzer.DocumentAnalyzer;

import com.as.text_understanding.common.LogInit;

import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpParser;
import de.tudarmstadt.ukp.dkpro.core.opennlp.OpenNlpSegmenter;

/**
 * Activates UIMA Document Analyzer to run UIMA PASTA annotator (and all its preconditions).
 * <br/>
 * <b>Note that the "Predicate" annotation in the document analyzer shows a full span of the predicate subtree, which contains,
 * in most cases, some arguments. Thus, the field "verb" of the "Predicate" annotation should be looked-at to see the
 * actual predicate.</b> In Document Analyzer click the predicate annotation, open it in the right pane, and look for the field "verb".
 *
 * <br/><br/>
 * Date: Aug 8, 2016
 * @author Asher Stern
 *
 */
public class DemoPastaAnnotator
{

	/**
	 * Entry point
	 * @param args a single argument of a directory in the file system (can be an empty temporary directory).
	 */
	public static void main(String[] args)
	{
		try
		{
			LogInit.init();
			
			File baseDir = new File(args[0]);

			AnalysisEngineDescription pastaDesc = AnalysisEngineFactory.createEngineDescription(FromDkproPastaAnnotator.class);
			
			AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(
					AnalysisEngineFactory.createEngineDescription(OpenNlpSegmenter.class),
					AnalysisEngineFactory.createEngineDescription(OpenNlpParser.class),
					pastaDesc
					);
			desc.getAnalysisEngineMetaData().setCapabilities(pastaDesc.getAnalysisEngineMetaData().getCapabilities());
			
			desc.toXML(new FileOutputStream(new File(baseDir, "desc.xml")));
			
			Thread.sleep(1000);
			
			SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					DocumentAnalyzer documentAnalyzer = new DocumentAnalyzer();
					documentAnalyzer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					documentAnalyzer.pack();
					documentAnalyzer.setVisible(true);
				}
			});

			
			
//			AnalysisEngineDescription desc = AnalysisEngineFactory.createEngineDescription(FromDkproPastaAnnotator.class);
//			desc.toXML(System.out);
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
	}
}
