package com.as.text_understanding.representation.pasta;

import java.util.List;

/**
 * 
 *
 * Date: Feb 24, 2016
 * @author Asher Stern
 *
 */
public class PredicateAndArguments
{
	public PredicateAndArguments(Predicate predicate, List<Argument> arguments)
	{
		super();
		this.predicate = predicate;
		this.arguments = arguments;
	}
	
	
	
	public Predicate getPredicate()
	{
		return predicate;
	}
	public List<Argument> getArguments()
	{
		return arguments;
	}



	private final Predicate predicate;
	private final List<Argument> arguments;
}
