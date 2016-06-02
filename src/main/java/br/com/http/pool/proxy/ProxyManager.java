package br.com.http.pool.proxy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpHost;

import br.com.http.pool.proxy.impl.HideMyAss;
import br.com.http.pool.proxy.impl.MultiProxy;

/**
 *
 * May 31, 2016
 * @author rjesus
 */
public final class ProxyManager {

	private static Set<HttpHost> listProxy = intializeProxies();
	private static Iterator<HttpHost> fastProxies;
	private static ArrayList<HttpHost> tempListIterable;

	/**
	 * @return the listProxy
	 */
	public static Set<HttpHost> getListProxy()
	{
		return listProxy;
	}

	/**
	 * @param listProxy
	 *            the listProxy to set
	 */
	public static void setListProxy(Set<HttpHost> listProxy)
	{
		ProxyManager.listProxy = listProxy;
	}

	/**
	 *
	 * @return
	 * May 31, 2016
	 * @author rjesus
	 */
	private static Set<HttpHost> intializeProxies()
	{
		Set<HttpHost> proxies = new HashSet<HttpHost>();
		/*
		 * proxies.addAll(new FreeProxyCH().load()); proxies.addAll(new HideMyAss().load()); proxies.addAll(new MultiProxy().load()); proxies.addAll(new TubeIncreaser().load());
		 */
		//proxies.addAll(new TubeIncreaser().load());
		proxies.addAll(new MultiProxy().load()); 
		//proxies.addAll(new FreeProxyCH().load());
		proxies.addAll(new HideMyAss().load());
		tempListIterable = new ArrayList<HttpHost>();
		addIterableList(proxies, tempListIterable);
		System.out.println("********Total:" + proxies.size() + "********");
		return proxies;
	}

	/**
	 *
	 * @return
	 * Jun 1, 2016
	 * @author rjesus
	 */
	public static Set<HttpHost> reload()
	{
		Set<HttpHost> proxies = new HashSet<HttpHost>();
		proxies.addAll(new HideMyAss().loadSingleProxyList());
		proxies.addAll(listProxy);
		tempListIterable = new ArrayList<HttpHost>();
		addIterableList(proxies, tempListIterable);
		return proxies;
	}

	/**
	 * Prevents error by multiple manipulation in list object 
	 * @param listHashSet
	 * @param tempListIterable
	 * May 31, 2016
	 * @author rjesus
	 */
	protected static void addIterableList(Set<HttpHost> listHashSet, List<HttpHost> tempListIterable)
	{
		tempListIterable.clear();
		tempListIterable.addAll(listHashSet);
		fastProxies = tempListIterable.iterator();
	}

	/**
	 * retrieve the next available proxy
	 * @return
	 * May 31, 2016
	 * @author rjesus
	 */
	public static HttpHost retrieveNextProxy()
	{
		if (!fastProxies.hasNext())
			addIterableList(listProxy, tempListIterable);
		return fastProxies.next();
	}
}