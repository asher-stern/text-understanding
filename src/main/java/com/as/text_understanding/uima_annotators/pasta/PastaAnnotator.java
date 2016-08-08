package com.as.text_understanding.uima_annotators.pasta;

import java.util.LinkedList;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;

import com.as.text_understanding.common.TextUnderstandingException;
import com.as.text_understanding.pasta.Pasta;
import com.as.text_understanding.representation.pasta.Argument;
import com.as.text_understanding.representation.pasta.ArgumentType;
import com.as.text_understanding.representation.pasta.PredicateAndArguments;
import com.as.text_understanding.representation.tree.Tree;
import com.as.text_understanding.tree_travel.TreeTravelNode;
import com.as.text_understanding.tree_util.item.ItemFinder;
import com.as.text_understanding.uima_typesystem.pasta.ArgumentItem;



/**
 * 
 *
 * Date: Mar 14, 2016
 * @author Asher Stern
 *
 */

public abstract class PastaAnnotator extends JCasAnnotator_ImplBase
{

	
	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException
	{
		List<Tree> trees = extractTreesFromCas(aJCas);
		for (Tree tree : trees)
		{
			List<PredicateAndArguments> pases = findPredicateAndArguments(aJCas, tree);
			pasResultToCas(aJCas, pases);
		}
	}

	protected abstract List<Tree> extractTreesFromCas(JCas aJCas);

	protected List<PredicateAndArguments> findPredicateAndArguments(JCas aJCas, Tree tree)
	{
		TreeTravelNode treeTravel = TreeTravelNode.createFromTree(tree.getRoot());
		Pasta pasta = new Pasta(treeTravel);
		pasta.annotate();
		List<PredicateAndArguments> pasResult = pasta.getResult();
		return pasResult;
	}


	
	protected void pasResultToCas(JCas aJcas, final List<PredicateAndArguments> pasResult)
	{
		for (PredicateAndArguments predArgs : pasResult)
		{
			com.as.text_understanding.uima_typesystem.pasta.PredicateAndArguments uimaPredicateAndArguments = new com.as.text_understanding.uima_typesystem.pasta.PredicateAndArguments(aJcas);
			
			int predArgsBegin = aJcas.getDocumentText().length();
			int predArgsEnd = 0;
			
			TreeTravelNode predicateSubtree = predArgs.getPredicate().getSubtree();
			int predicateBegin = predicateSubtree.getItself().getItem().getBegin();
			int predicateEnd = predicateSubtree.getItself().getItem().getEnd();
			predArgsBegin = Math.min(predArgsBegin, predicateBegin);
			predArgsEnd = Math.max(predArgsEnd, predicateEnd);
			com.as.text_understanding.uima_typesystem.pasta.Predicate uimaPredicate = new com.as.text_understanding.uima_typesystem.pasta.Predicate(aJcas);
			uimaPredicate.setBegin(predicateBegin);
			uimaPredicate.setEnd(predicateEnd);
			
			TreeTravelNode verbNode = predArgs.getPredicate().getVerbNode();
			Annotation verb = new Annotation(aJcas);
			verb.setBegin(verbNode.getItself().getItem().getBegin());
			verb.setEnd(verbNode.getItself().getItem().getEnd());
			uimaPredicate.setVerb(verb);
			
			verb.addToIndexes();
			uimaPredicate.addToIndexes();
			
			uimaPredicateAndArguments.setPredicate(uimaPredicate);


			if (predArgs.getArguments()!=null)
			{
				uimaPredicateAndArguments.setArguments(new FSArray(aJcas, predArgs.getArguments().size()));
				int argumentIndex = 0;
				for (Argument argument : predArgs.getArguments())
				{
					com.as.text_understanding.uima_typesystem.pasta.Argument uimaArgument = new com.as.text_understanding.uima_typesystem.pasta.Argument(aJcas);
					int argumentBegin = argument.getSubtree().getItself().getItem().getBegin();
					int argumentEnd = argument.getSubtree().getItself().getItem().getEnd();
					predArgsBegin = Math.min(predArgsBegin, argumentBegin);
					predArgsEnd = Math.max(predArgsEnd, argumentEnd);
					uimaArgument.setBegin(argumentBegin);
					uimaArgument.setEnd(argumentEnd);
					uimaArgument.addToIndexes();
					uimaArgument.setClause(argument.isClause());

					uimaArgument.setArgumentType(createUimaArgumentType(argument.getType(), aJcas));

					LinkedList<ArgumentItem> listArgumentItems = new LinkedList<>();
					ItemFinder itemFinder = new ItemFinder();
					List<List<TreeTravelNode>> items = itemFinder.findItems(argument.getSubtree());
					for (List<TreeTravelNode> item : items)
					{
						if (!item.isEmpty())
						{
							int itemBegin = item.get(0).getItself().getItem().getBegin();
							int itemEnd = item.get(item.size()-1).getItself().getItem().getEnd();
							if (itemBegin>=itemEnd) throw new TextUnderstandingException("Anomaly: item end preceds begin.");
							ArgumentItem argumentItem = new ArgumentItem(aJcas);
							argumentItem.setBegin(itemBegin);
							argumentItem.setEnd(itemEnd);
							argumentItem.addToIndexes();
							listArgumentItems.add(argumentItem);
						}
					}
					FSArray argumentItemArray = new FSArray(aJcas, listArgumentItems.size());
					int itemIndex=0;
					for (ArgumentItem argumentItem : listArgumentItems)
					{
						argumentItemArray.set(itemIndex, argumentItem);
						++itemIndex;
					}
					uimaArgument.setItems(argumentItemArray);
					if (argument.getPreposition()!=null)
					{
						if (argument.getPreposition().size()>0)
						{
							uimaArgument.setPrepositions(new FSArray(aJcas, argument.getPreposition().size()));
							int prepositionIndex=0;
							for (TreeTravelNode preposition : argument.getPreposition())
							{
								Annotation prepositionAnnotation = new Annotation(aJcas);
								prepositionAnnotation.setBegin(preposition.getItself().getItem().getBegin());
								prepositionAnnotation.setEnd(preposition.getItself().getItem().getEnd());
								prepositionAnnotation.addToIndexes();
								uimaArgument.setPrepositions(prepositionIndex, prepositionAnnotation);
								++prepositionIndex;
							}
						}
					}
					uimaPredicateAndArguments.setArguments(argumentIndex, uimaArgument);

					++argumentIndex;
				}
			}
			uimaPredicateAndArguments.setBegin(predArgsBegin);
			uimaPredicateAndArguments.setEnd(predArgsEnd);
			uimaPredicateAndArguments.addToIndexes();
		}
	}
	
	private static com.as.text_understanding.uima_typesystem.pasta.ArgumentType createUimaArgumentType(ArgumentType argumentType, JCas aJcas)
	{
		com.as.text_understanding.uima_typesystem.pasta.ArgumentType ret = null;
		switch(argumentType)
		{
		case SUBJECT:
			ret = new com.as.text_understanding.uima_typesystem.pasta.Subject(aJcas);
			break;
		case OBJECT:
			ret = new com.as.text_understanding.uima_typesystem.pasta.Object(aJcas);
			break;
		case MODIFIER:
			ret = new com.as.text_understanding.uima_typesystem.pasta.Modifier(aJcas);
			break;
		case UNKNOWN:
			ret = new com.as.text_understanding.uima_typesystem.pasta.Unknown(aJcas);
			break;
		default:
			throw new TextUnderstandingException("Bad type.");
				
		}
		ret.addToIndexes();
		return ret;
	}
}
