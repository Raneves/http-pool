package br.com.http.pool.proxy.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpHost;
import org.jsoup.Jsoup;

import br.com.http.pool.proxy.IProxy;
import br.com.http.pool.proxy.ProxyBase;
import br.com.http.pool.proxy.exception.ProxyNotFoundException;

public class DailyProxy extends ProxyBase implements IProxy {

	private static Logger LOG = Logger.getLogger(DailyProxy.class.getName());

	private List<HttpHost> loadTXT()
	{
		proxies = new ArrayList<HttpHost>();
		try 
		{
			InputStream resourceFile = getClass().getResourceAsStream("/dailyproxies.txt");
			doc = Jsoup.parse(resourceFile, "UTF-8", "/dailyproxies.txt");
			if (doc != null && doc.hasText()) 
			{
				proxies = loadSimpleTXTFormat(doc);
				if (proxies.isEmpty())
					throw new ProxyNotFoundException(doc);
			}
			
			LOG.log(Level.INFO, "Proxies total dailyproxies: " + proxies.size());
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

	public List<HttpHost> load() {
		return loadTXT();
	}
	
}