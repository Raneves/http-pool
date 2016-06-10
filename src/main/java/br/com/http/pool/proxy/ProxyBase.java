/**
 * 
 */
package br.com.http.pool.proxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpHost;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import br.com.http.pool.proxy.exception.ProxyNotFoundException;

/**
 *
 * May 31, 2016</br>
 * Description of file ProxyBase.java: Common methods to read a list of proxies on request return or txt file
 * @author rjesus
 */
public abstract class ProxyBase {
	
	private static Logger LOG = Logger.getLogger(ProxyBase.class.getName());

	protected Document doc;
	protected List<HttpHost> proxies;

	/**
	 *
	 * @param ip
	 * @param port
	 * May 31, 2016
	 * @author rjesus
	 */
	protected void addProxy(String ip, int port) {
		try 
		{
			proxies.add(new HttpHost(ip, port));
		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Proxy not added: " + e.getMessage());
		}
	}

	/**
	 * Default method for read a list of proxies from txt file
	 * @param url
	 * @return
	 * May 31, 2016
	 * @author rjesus
	 */
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

	/**
	 * Load and return a list with all checked proxies from document
	 * @param doc
	 * @return
	 * May 31, 2016
	 * @author rjesus
	 */
	protected List<HttpHost> loadSimpleTXTFormat(Document doc)
	{
		String[] lines;
		lines = doc.text().split(" ");
		for (String line : lines) 
		{
			try
			{
				String ip = line.split(":")[0];
				int port = Integer.parseInt(line.split(":")[1]);
				addProxy(ip, port);
			}catch(ArrayIndexOutOfBoundsException e)
			{
				LOG.log(Level.SEVERE, "File contains unexpected characters on line: " + line);
			}
		}
		return proxies;
	}
}