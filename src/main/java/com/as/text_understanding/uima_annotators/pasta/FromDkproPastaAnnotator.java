package com.as.text_understanding.uima_annotators.pasta;

import java.util.List;

import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;

import com.as.text_understanding.representation.tree.Tree;
import com.as.text_understanding.tree_util.TreeBuilderFromDkpro;


/**
 * 
 *
 * Date: Mar 14, 2016
 * @author Asher Stern
 *
 */

@TypeCapability(
		inputs = {
				"de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent",
		"de.tudarmstadt.ukp.dkpro.core.api.syntax.type.PennTree"},
		outputs = {
				"com.as.text_understanding.uima_typesystem.pasta.Argument",
				"com.as.text_understanding.uima_typesystem.pasta.ArgumentItem",
				"com.as.text_understanding.uima_typesystem.pasta.ArgumentType",
				"com.as.text_understanding.uima_typesystem.pasta.Modifier",
				"com.as.text_understanding.uima_typesystem.pasta.Object",
				"com.as.text_understanding.uima_typesystem.pasta.Predicate",
				"com.as.text_understanding.uima_typesystem.pasta.PredicateAndArguments",
				"com.as.text_understanding.uima_typesystem.pasta.Subject",
		"com.as.text_understanding.uima_typesystem.pasta.Unknown"})
public class FromDkproPastaAnnotator extends PastaAnnotator
{
	@Override
	protected List<Tree> extractTreesFromCas(JCas aJCas)
	{
		TreeBuilderFromDkpro builder = new TreeBuilderFromDkpro(aJCas);
		return builder.buildForMultiSentence();
	}
}
