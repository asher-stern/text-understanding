package com.as.text_understanding.representation.pasta;

import java.util.List;

/**
 * Encapsulates a predicate and its arguments.
 * Note that in one sentence there might be several {@link PredicateAndArguments}. Thus the PASTA result of a single sentence is
 * a list of {@link PredicateAndArguments}.
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
