/**
 * 
 */
package br.com.http.pool.proxy.impl;

import java.util.List;

import org.apache.http.HttpHost;

import br.com.http.pool.proxy.IProxy;
import br.com.http.pool.proxy.ProxyBase;

/**
 *
 * Jun 1, 2016 </br>
 * Description of file MultiProxy.java: many proxies around the world
 * @author rjesus
 */
public class MultiProxy extends ProxyBase implements IProxy {

	private static final String MULTI_PROXY = "http://multiproxy.org/txt_anon/proxy.txt";

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.http.pool.proxy.IProxy#load()
	 */
	public List<HttpHost> load()
	{
		return defaultLoadTXT(MULTI_PROXY);
	}

}
