package com.as.text_understanding.common;

import java.io.File;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * 
 *
 * Date: Mar 9, 2016
 * @author asher
 *
 */
public class LogInit
{
	public static final String LOG4J_PROPERTIES = "log4j.properties";

	public static void init()
	{
		final File log4jProperties = new File(LOG4J_PROPERTIES);
		if (log4jProperties.exists())
		{
			PropertyConfigurator.configure(log4jProperties.getPath());
		}
		else
		{
			BasicConfigurator.configure();
			Logger.getRootLogger().setLevel(Level.INFO);
		}
	}
}
