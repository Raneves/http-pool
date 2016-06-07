package br.com.http.pool.service;

import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

import br.com.http.pool.method.GetRequestMethod;

/**
 *
 * May 31, 2016 Description of file HttpPoolService.java: manager the ConnectionManager object and requests.
 * 
 * @author rjesus
 */
public class HttpPoolService extends Thread {

	private static Logger LOG = Logger.getLogger(HttpPoolService.class.getName());
	
	public static final int MAX_CONNECTIONS = 4000;
	private static final int MAX_HEADER_COUNT = 200;
	private static final int MAX_LINE_LENGTH = 2000;
	/**
	 * after one request sleep current thread for 1 second
	 */
	private static final long TIME_SLEEP = 1000;
	
	/**
	 * In a critical error wait until 10 seconds and close all connections
	 */
	private static final int TIME_SLEEP_EXCEPTION = 10000;
	
	/**
	 * User agent for all connections
	 */
	private static final String USER_AGENT = "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)";
	
	private PoolingHttpClientConnectionManager connectionManager;
	private CloseableHttpClient httpClient;
	private String host;
	private String url;
	private int numberOfRequests;
	private boolean logOption;
	private RequestConfig defaultRequestConfig;
	
	/**
	 * @param url
	 * @param sizeOfPool
	 * @param logOption
	 * May 31, 2016
	 * @author rjesus
	 */
	public HttpPoolService(String url, int numberOfRequests, boolean logOption)
	{
		host = url.replace("http://", "").split("/")[0];
		this.url = url;
		this.numberOfRequests = numberOfRequests;
		this.logOption = logOption;
		
		connectionManager = buildConnectionManager();
        httpClient = buildHttpClient();
	}

	/**
	 *
	 * @return
	 * May 31, 2016
	 * @author rjesus
	 */
	public PoolingHttpClientConnectionManager getConnectionManager()
	{
		return connectionManager;
	}
	
	/**
	 *
	 * @return
	 * May 31, 2016
	 * @author rjesus
	 */
	public CloseableHttpClient getHttpClient()
	{
		return httpClient;
	}

	/**
	 * Creates a httpClient with default configuration for httpPool Service</br>
	 * Example from https://hc.apache.org/httpcomponents-client-ga/httpclient/examples/org/apache/http/examples/client/ClientConfiguration.java
	 * @return
	 * May 31, 2016
	 * @author rjesus
	 */
	private CloseableHttpClient buildHttpClient()
	{
		// Use custom cookie store if necessary.
        CookieStore cookieStore = new BasicCookieStore();
        
        // Create global request configuration
        defaultRequestConfig = RequestConfig.custom()
            .setCookieSpec(CookieSpecs.DEFAULT)
            .setExpectContinueEnabled(true)
            .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
            .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
            .build();

        // Create an HttpClient with the given custom dependencies and configuration.
        CloseableHttpClient httpclient = HttpClients.custom()
        	.setUserAgent(USER_AGENT)
            .setConnectionManager(connectionManager)
            .setDefaultCookieStore(cookieStore)
            .setDefaultRequestConfig(defaultRequestConfig)
            .build();
        
        return httpclient;
	}

	/**
	 * Creates a connection manager with default service configuration</br>
	 * Example from https://hc.apache.org/httpcomponents-client-ga/httpclient/examples/org/apache/http/examples/client/ClientConfiguration.java
	 * @return
	 * May 31, 2016
	 * @author rjesus
	 */
	private PoolingHttpClientConnectionManager buildConnectionManager()
	{
		Registry<ConnectionSocketFactory> socketFactoryRegistry = defaultSocketFactoryRegistry();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		 
		// Create socket configuration
        SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
        
        // Configure the connection manager to use socket configuration either
        // by default or for a specific host.
        connectionManager.setDefaultSocketConfig(socketConfig);
        connectionManager.setSocketConfig(new HttpHost(host, 80), socketConfig);
        
        // Validate connections after x seconds of inactivity
        //disabled
        connectionManager.setValidateAfterInactivity(-1);

        // Create message constraints
        MessageConstraints messageConstraints = MessageConstraints.custom()
            .setMaxHeaderCount(MAX_HEADER_COUNT)
            .setMaxLineLength(MAX_LINE_LENGTH)
            .build();
        
        // Create connection configuration
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
            .setMalformedInputAction(CodingErrorAction.IGNORE)
            .setUnmappableInputAction(CodingErrorAction.IGNORE)
            .setCharset(Consts.UTF_8)
            .setMessageConstraints(messageConstraints)
            .build();
        
        // Configure the connection manager to use connection configuration either
        // by default or for a specific host.
        connectionManager.setDefaultConnectionConfig(connectionConfig);
        connectionManager.setConnectionConfig(new HttpHost(host, 80), ConnectionConfig.DEFAULT);

        // Configure total max or per route limits for persistent connections
        // that can be kept in the pool or leased by the connection manager.
        connectionManager.setMaxTotal(MAX_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(MAX_CONNECTIONS);
        connectionManager.setMaxPerRoute(new HttpRoute(new HttpHost(host, 80)), MAX_CONNECTIONS);
        return connectionManager;
	}
	
	/**
	 * example from here https://hc.apache.org/httpcomponents-client-ga/httpclient/examples/org/apache/http/examples/client/ClientConfiguration.java
	 * @return
	 * May 31, 2016
	 * @author rjesus
	 */
	public Registry<ConnectionSocketFactory> defaultSocketFactoryRegistry() 
	{
		// SSL context for secure connections can be created either based on
        // system or application specific properties.
        SSLContext sslcontext = SSLContexts.createSystemDefault();

        // Create a registry of custom connection socket factories for supported
        // protocol schemes.
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.INSTANCE)
            .register("https", new SSLConnectionSocketFactory(sslcontext))
            .build();
        
		return socketFactoryRegistry;
	}

	/**
	 * sleeping and log exception
	 * @param time
	 * May 31, 2016
	 * @author rjesus
	 */
	private void sleep(int time)
	{
		try 
		{
			Thread.sleep(time);
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
			LOG.log(Level.SEVERE, "\n\n\n\n\n\n\n\n\n\nSleeping Critical Error: " + e.getMessage() + "\n\n\n\n\n\n\n\n\n\n");
		}
	}
	
	/**
	 * calls connectionManager->shutdown() method and builds a new instance of cm and httpClient
	 * May 31, 2016
	 * @author rjesus
	 */
	protected void renewManager()
	{
		if (connectionManager.getTotalStats().getLeased() > (MAX_CONNECTIONS - 50))
		{
			connectionManager.shutdown();
			sleep(5000);
			connectionManager = buildConnectionManager();
			httpClient = buildHttpClient();
			sleep(5000);
		}
	}
	
	@Override
	public void run()
	{
		while (true) {
			try 
			{
				for (int cont = 0; cont < numberOfRequests; cont++) 
				{
					new GetRequestMethod(url, httpClient, defaultRequestConfig, logOption).start();
				}
				
				LOG.log(Level.INFO, "\n---------------Send REQUEST " + numberOfRequests + "---------------\n");
				//System.out.println("\n---------------Send REQUEST " + numberOfRequests + "---------------\n");
				sleep(TIME_SLEEP);
			}
			catch (OutOfMemoryError m) 
			{
				LOG.log(Level.SEVERE, "\n\n\n\n\n\n\n\n\n\nMemory Critical Error: " + m.getMessage() + "\n\n\n\n\n\n\n\n\n\n");
				sleep(TIME_SLEEP_EXCEPTION);
				renewManager();

			}
			catch (Throwable t) 
			{
				LOG.log(Level.SEVERE, "\n\n\n\n\n\n\n\n\n\nCritical Error!\n");
				t.printStackTrace();
				sleep(TIME_SLEEP_EXCEPTION);
				renewManager();
			}
		}
	}
}