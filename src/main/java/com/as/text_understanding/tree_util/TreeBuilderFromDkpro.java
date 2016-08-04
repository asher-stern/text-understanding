package com.as.text_understanding.tree_util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;

import com.as.text_understanding.common.TextUnderstandingException;
import com.as.text_understanding.representation.tree.Terminal;
import com.as.text_understanding.representation.tree.Tree;
import com.as.text_understanding.representation.tree.TreeItem;
import com.as.text_understanding.representation.tree.TreeNode;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.constituent.Constituent;

/**
 * 
 * Date: March 1, 2016
 * @author asher
 *
 */
public class TreeBuilderFromDkpro
{
	
	public TreeBuilderFromDkpro(JCas cas)
	{
		super();
		this.cas = cas;
	}
	
	public Tree buildForSingleSentence()
	{
		return new Tree(build(findRoot()));
	}
	
	public List<Tree> buildForMultiSentence()
	{
		List<Constituent> rootConstituents = findRootsForAllSentences();
		List<Tree> ret = new ArrayList<>(rootConstituents.size());
		for (Constituent constituent : rootConstituents)
		{
			ret.add( new Tree(build(constituent)) );
		}
		return ret;
	}



	private TreeNode build(Constituent treeRoot)
	{
		FSArray childrenArray = treeRoot.getChildren();
		final int numberOfChildren = childrenArray.size();
		List<TreeNode> children = new ArrayList<>(numberOfChildren);
		
		for (int childIndex = 0; childIndex<numberOfChildren; ++childIndex)
		{
			FeatureStructure childFS = childrenArray.get(childIndex);
			if (childFS instanceof Constituent)
			{
				children.add(build( (Constituent)childFS ));
			}
			else if (childFS instanceof Token)
			{
				Token token = (Token)childFS;
				String tag = token.getPos().getPosValue();
				String tokenString = token.getCoveredText();
				TreeItem item = new TreeItem(new Terminal(tokenString, tag));
				item.setBeginEnd(token.getBegin(), token.getEnd());
				children.add( new TreeNode(item, null) );
			}
			else
			{
				throw new TextUnderstandingException("Unrecognized child with type: "+childFS.getClass().getName());
			}
		}
		
		final String symbol = treeRoot.getClass().getSimpleName();
		TreeItem item = new TreeItem(symbol);
		item.setBeginEnd(treeRoot.getBegin(), treeRoot.getEnd());
		return new TreeNode(item, children);
	}
	
	private List<Constituent> findRootsForAllSentences()
	{
		List<Constituent> ret = new LinkedList<>();
		
		JCasUtil.indexCovered(cas, Sentence.class, Constituent.class);
		
		for (Annotation sentenceAnnotation : cas.getAnnotationIndex(Sentence.type))
		{
			Sentence sentence = (Sentence) sentenceAnnotation;
			List<Constituent> coveredConstituents = JCasUtil.selectCovered(cas, Constituent.class, sentence);
			for (Constituent constituent : coveredConstituents)
			{
				if ( (constituent.getBegin()==sentence.getBegin()) && (constituent.getEnd()==sentence.getEnd()) )
				{
					ret.add(constituent);
					break;
				}
			}
		}
		
		return ret;
	}
	
	private Constituent findRoot()
	{
		final int length = cas.getDocumentText().length();
		AnnotationIndex<Annotation> allConstituents = cas.getAnnotationIndex(Constituent.type);
		Constituent topConstituent = null;
		for (Annotation annotation : allConstituents)
		{
			if ( (annotation.getBegin()==0) && (annotation.getEnd()==length) )
			{
				topConstituent = (Constituent) annotation;
				break;
			}
		}
		if (topConstituent==null) {throw new TextUnderstandingException("Could not find root.");}
		return topConstituent;
	}


	// input
	private final JCas cas;
}
