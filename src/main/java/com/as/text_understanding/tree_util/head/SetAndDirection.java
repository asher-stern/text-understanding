package com.as.text_understanding.tree_util.head;

import java.util.Set;

/**
 * 
 *
 * Date: Mar 10, 2016
 * @author asher
 *
 */
public class SetAndDirection
{
	public SetAndDirection(Set<String> set, Direction direction)
	{
		super();
		this.set = set;
		this.direction = direction;
	}
	
	
	
	public Set<String> getSet()
	{
		return set;
	}
	public Direction getDirection()
	{
		return direction;
	}



	private final Set<String> set;
	private final Direction direction;
}
