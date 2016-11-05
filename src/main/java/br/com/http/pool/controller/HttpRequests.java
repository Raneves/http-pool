package br.com.http.pool.controller;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import br.com.http.pool.proxy.IdleConnectionMonitorThread;
import br.com.http.pool.proxy.ProxyReloader;
import br.com.http.pool.service.HttpPoolService;

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
		String url = parameters[0];
		int numberOfRequests = Integer.parseInt(parameters[1]);
		boolean logOption = new Boolean(parameters[2]);
		enableLog(logOption);
		HttpPoolService service = new HttpPoolService(url, numberOfRequests, logOption);
		PoolingHttpClientConnectionManager connectionManager = service.getConnectionManager(); 
		ProxyReloader proxyReloader = new ProxyReloader();
		IdleConnectionMonitorThread monitor = new IdleConnectionMonitorThread(connectionManager);
		
		service.start();
		monitor.start();
		proxyReloader.start();
		
		LOG.log(Level.INFO, "Starting HTTP-POOL");
	}

	private static void enableLog(boolean enableLog)
	{
		if(!enableLog)
		{
			LogManager.getLogManager().reset();
			Logger globalLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
			globalLogger.setLevel(Level.OFF);
		}
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
		String regexOfNumberOfRequests = "[0-9]+";
		
		if(parameters == null || parameters.length != 3)
		{
			msg = "Three parameters are required: [{URL}] [{NUMBER_OF_REQUESTS}] [{LOG_OPTION}]";
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
		
		if(sizeOfPool == null || !sizeOfPool.matches(regexOfNumberOfRequests))
		{
			msg = "invalid number of requests: " + sizeOfPool;
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