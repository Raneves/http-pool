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
 * Description of file TubeIncreaser.java: many proxies around the world. load by txt file
 * 
 * @author rjesus
 */
public class TubeIncreaser extends ProxyBase implements IProxy {
	private static final String TUBE_INCREASER = "http://www.tubeincreaser.com/proxylist.txt";

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.http.pool.proxy.IProxy#load()
	 */
	public List<HttpHost> load()
	{
		return defaultLoadTXT(TUBE_INCREASER);
	}

}
