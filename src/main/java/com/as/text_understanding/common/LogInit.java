package com.as.text_understanding.common;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * 
 *
 * Date: Mar 9, 2016
 * @author asher
 *
 */
public class LogInit
{
	public static void init()
	{
		BasicConfigurator.configure();
		Logger.getRootLogger().setLevel(Level.INFO);
	}
}
