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
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import br.com.http.pool.proxy.IProxy;
import br.com.http.pool.proxy.ProxyBase;
import br.com.http.pool.proxy.exception.ProxyNotFoundException;

/**
 *
 * May 31, 2016 </br>
 * Description of file FreeProxyCH.java: many proxies around the world
 * @author rjesus
 */
public class FreeProxyCH extends ProxyBase implements IProxy {

	private static Logger LOG = Logger.getLogger(FreeProxyCH.class.getName());
	/**
	 * the url
	 */
	private static final String FREE_PROXY_CH = "http://www.freeproxy.ch/proxy.txt";
	private static final String FIELD_INVALID = "------------------------------------------------------------------------------------------------";

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.http.pool.proxy.IProxy#load()
	 */
	public List<HttpHost> load()
	{
		proxies = new ArrayList<HttpHost>();
		String body;
		try {
			HttpResponse response = HttpClients.createMinimal().execute(new HttpGet(FREE_PROXY_CH));

			if (response != null) 
			{
				body = EntityUtils.toString(response.getEntity());
				proxies = loadFreeProxyCH(body);
				if (proxies.isEmpty())
					throw new ProxyNotFoundException(doc);
			}

			LOG.log(Level.INFO, "Total FreeProxy: " + proxies.size());
		}
		catch (IOException e)
		{
			LOG.log(Level.SEVERE, "IO Exception: " + e.getMessage(), e);
		}
		catch (ProxyNotFoundException e)
		{
			LOG.log(Level.SEVERE, e.getMessage(), e);
		}
		catch (Exception e) 
		{
			LOG.log(Level.SEVERE, "Unexpected Exception: " + e.getMessage(), e);
		}

		return proxies;
	}

	/**
	 *
	 * @param body
	 * @return Jun 1, 2016
	 * @author rjesus
	 */
	private List<HttpHost> loadFreeProxyCH(String body)
	{
		String[] elements = body.replaceAll("\\r", "").trim().split(FIELD_INVALID)[1].split("\\n");
		for (String element : elements)
		{
			if (!element.isEmpty()) 
			{
				String ip = element.split(":")[0];
				int port = Integer.parseInt(element.split(":")[1].split("	")[0]);
				addProxy(ip, port);
			}
		}
		return proxies;
	}

}