/**
 * 
 */
package br.com.http.pool.proxy.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpHost;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.http.pool.proxy.IProxy;
import br.com.http.pool.proxy.ProxyBase;
import br.com.http.pool.proxy.exception.ProxyNotFoundException;

/**
 *
 * Jun 1, 2016</br>
 * Description of file HideMyAss.java: Many proxies around the world.
 * @author rjesus
 */
public class HideMyAss extends ProxyBase implements IProxy {
	
	private static Logger LOG = Logger.getLogger(HideMyAss.class.getName());

	private final String HIDE_MY = "http://proxylist.hidemyass.com/";
	private final String CSS_DISPLAY_STYLE = "{display:none}";

	public List<HttpHost> load()
	{
		Elements elementsTR = null;
		String url = null;
		proxies = new ArrayList<HttpHost>();
		for (int pagina = 1; pagina <= 26; pagina++)
		{
			try 
			{
				url = HIDE_MY + pagina;
				doc = Jsoup.connect(url).get();
				if (doc != null && doc.hasText()) 
				{
					elementsTR = doc.getElementsByTag("tr");
					for (Element element : elementsTR) 
					{
						element = cleanElement(element);
						String ip = element.child(1).text();
						if (ip.contains(".")) 
						{
							int port = Integer.parseInt(element.child(2).text());
							addProxy(ip, port);
						}
					}
					if (proxies.isEmpty())
						throw new ProxyNotFoundException(doc);
				}
			}
			catch (IOException e)
			{
				LOG.log(Level.SEVERE, "IOExceptin: " + e.getMessage(), e);
			}
			catch (ProxyNotFoundException e) 
			{
				LOG.log(Level.SEVERE, e.getMessage(), e);
			}
			catch (Exception e) 
			{
				LOG.log(Level.SEVERE, "Unexpected exception in url " + url + ": " + e.getMessage(), e);
			}
		}
		
		LOG.log(Level.INFO, "Total Proxies Hide My: " + proxies.size());
		return proxies;
	}

	/**
	 * Return just html elements that are visible for UI
	 * @param element
	 * @return
	 * Jun 1, 2016
	 * @author rjesus
	 */
	private Element cleanElement(Element element)
	{
		element.child(1).select("[style*=none]").remove();
		String[] selectors = element.child(1).data().split("\\.");
		
		for (String selector : selectors)
		{
			if (selector.contains(CSS_DISPLAY_STYLE))
			{
				element.child(1).select("[class*=" + selector.substring(0, selector.indexOf(CSS_DISPLAY_STYLE)) + "]").remove();
			}
		}
		
		return element;
	}

	/**
	 * load proxies by first page of website. It is simpler than load all pages.
	 * @return
	 * Jun 1, 2016
	 * @author rjesus
	 */
	public Collection<? extends HttpHost> loadSingleProxyList()
	{
		Elements elementsTR = null;
		String url = null;
		proxies = new ArrayList<HttpHost>();

		try 
		{
			url = HIDE_MY + 1;
			doc = Jsoup.connect(url).get();
			
			if (doc != null && doc.hasText()) 
			{
				elementsTR = doc.getElementsByTag("tr");
				for (Element element : elementsTR) 
				{
					element = cleanElement(element);
					String ip = element.child(1).text();
					if (ip.contains(".")) 
					{
						int port = Integer.parseInt(element.child(2).text());
						addProxy(ip, port);
					}
				}
				
				if (proxies.isEmpty())
					throw new ProxyNotFoundException(doc);
			}
		}
		catch (IOException e)
		{
			LOG.log(Level.SEVERE, "IOException: " + e.getMessage(), e);
		}
		catch (ProxyNotFoundException e) 
		{
			LOG.log(Level.SEVERE, e.getMessage(), e);
		}
		catch (Exception e) {
			LOG.log(Level.SEVERE, "Unexpected exception in url " + url + ": " + e.getMessage(), e);
		}

		LOG.log(Level.INFO, "Total Proxies Hide My: " + proxies.size());
		return proxies;
	}

}
