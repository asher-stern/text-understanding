package com.as.text_understanding.uima_annotators.pasta;

import java.util.List;

import org.apache.uima.jcas.JCas;

import com.as.text_understanding.pasta.Pasta;
import com.as.text_understanding.representation.pasta.PredicateAndArguments;
import com.as.text_understanding.representation.tree.Tree;
import com.as.text_understanding.tree_travel.TreeTravelNode;
import com.as.text_understanding.tree_util.TreeBuilderFromDkpro;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;


/**
 * 
 *
 * Date: Mar 14, 2016
 * @author Asher Stern
 *
 */
public class FromDkproPastaAnnotator extends PastaAnnotator
{
	@Override
	protected List<Tree> extractTreesFromCas(JCas aJCas)
	{
		
		TreeBuilderFromDkpro builder = new TreeBuilderFromDkpro(aJCas);
		builder.build();
		Tree tree = builder.getTree();
		return tree;
	}
}
