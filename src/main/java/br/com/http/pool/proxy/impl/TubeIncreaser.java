/**
 * 
 */
package br.com.http.pool.proxy.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpHost;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import br.com.http.pool.proxy.IProxy;
import br.com.http.pool.proxy.ProxyBase;
import br.com.http.pool.proxy.exception.ProxyNotFoundException;

/**
 *
 * Jun 1, 2016 </br>
 * Description of file TubeIncreaser.java: many proxies around the world. load by txt file
 * 
 * @author rjesus
 */
public class TubeIncreaser extends ProxyBase implements IProxy {
	
	private static Logger LOG = Logger.getLogger(TubeIncreaser.class.getName());

	private static final String TUBE_INCREASER = "http://web.unideb.hu/aurel192/proxylist.txt";

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.http.pool.proxy.IProxy#load()
	 */
	public List<HttpHost> load()
	{
		return defaultLoadTXT(TUBE_INCREASER);
	}

	protected List<HttpHost> loadSimpleTXTFormat(Document doc)
	{
		String[] elements;
		elements = doc.text().split(" ");
		for (String element : elements) 
		{
			String ip = element.split(":")[0];
			int port = Integer.parseInt(element.split(":")[1]);
			addProxy(ip, port);
		}
		return proxies;
	}
	
	protected List<HttpHost> defaultLoadTXT(String url)
	{
		proxies = new ArrayList<HttpHost>();
		try 
		{
			doc = Jsoup.connect(url).get();
			if (doc != null && doc.hasText()) 
			{
				proxies = loadSimpleTXTFormat(doc);
				if (proxies.isEmpty())
					throw new ProxyNotFoundException(doc);
			}
			
			LOG.log(Level.INFO, "Proxies total " + url + ": " + proxies.size());
		}
		catch (IOException e) 
		{
			LOG.log(Level.SEVERE, "The proxy list don't be loaded: " + e.getMessage(), e);
		}
		catch (ProxyNotFoundException e) 
		{
			LOG.log(Level.SEVERE, e.getMessage(), e);
		}
		catch (Exception e) {
			LOG.log(Level.SEVERE, "Unexpected exception: " + e.getMessage(), e);
		}
		
		return proxies;
	}
}
