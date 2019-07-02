package fr.epita.quiz.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config 
{
	private Properties properties;
	private static Config config;
	
	private Config()
	{
		try
		{
			properties.load(new FileInputStream(new File("application.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Config getInstance()
	{
		if (config == null)
		{
			config = new Config();
		}
		return config;
	}
	
	public String getPropertyValue(String key)
	{
		return properties.getProperty(key);
	}
}
