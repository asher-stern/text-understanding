package com.as.text_understanding.tree_util;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.tcas.Annotation;

import com.as.text_understanding.TextUnderstandingException;
import com.as.text_understanding.representation.tree.Terminal;
import com.as.text_understanding.representation.tree.Tree;
import com.as.text_understanding.representation.tree.TreeItem;
import com.as.text_understanding.representation.tree.TreeNode;

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
	public void build()
	{
		tree = new Tree(build(findRoot()));
	}
	public Tree getTree()
	{
		return tree;
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
				children.add( new TreeNode(new TreeItem(new Terminal(tokenString, tag)), null) );
			}
			else
			{
				throw new TextUnderstandingException("Unrecognized child with type: "+childFS.getClass().getName());
			}
		}
		
		return new TreeNode(new TreeItem(treeRoot.getSyntacticFunction()),children);
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
	
	// output
	private Tree tree;
}
