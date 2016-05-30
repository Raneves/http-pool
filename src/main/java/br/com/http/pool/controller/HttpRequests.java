package br.com.http.pool.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * May 30, 2016
 * 
 * @author rjesus
 */
public class HttpRequests {

	private static Logger LOG = Logger.getLogger(HttpRequests.class.getName());

	public static void main(String... parameters)
	{
		validateParameters(parameters);
		LOG.log(Level.INFO, "requested url: " + parameters[0]);
	}

	/**
	 *
	 * @param parameters
	 *            May 30, 2016
	 * @author rjesus
	 */
	private static void validateParameters(String[] parameters)
	{
		String msg;
		String regexOfURL = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		String regexOfSizeOfPool = "[0-9]+";
		
		if(parameters == null || parameters.length == 0)
		{
			msg = "Three parameters are required: [{URL}] [{SIZE_OF_POOL}] [{LOG_OPTION}]";
			LOG.log(Level.SEVERE, msg);
			throw new RuntimeException(msg);	
		}
		
		String url = parameters[0];
		String sizeOfPool = parameters[1];
		String logOption = parameters[2];
		
		if (url == null || !url.matches(regexOfURL)) 
		{
			msg = "Invalid url: " + url;
			LOG.log(Level.SEVERE, msg);
			throw new RuntimeException(msg);	
		}
		
		if(sizeOfPool == null || !sizeOfPool.matches(regexOfSizeOfPool))
		{
			msg = "invalid size of pool: " + sizeOfPool;
            LOG.log(Level.SEVERE, msg);
			throw new RuntimeException(msg);
		}
		
		if(logOption == null || !(logOption.equalsIgnoreCase("true") || logOption.equalsIgnoreCase("false")))
		{
			msg = "invalid log option: " + logOption;
			LOG.log(Level.SEVERE, msg);																																																																																																																																																																																																												
			throw new RuntimeException(msg);
		}
	}

}