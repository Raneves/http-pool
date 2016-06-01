/**
 * 
 */
package br.com.http.pool.proxy.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;

import br.com.http.pool.proxy.IProxy;
import br.com.http.pool.proxy.ProxyBase;

/**
 *
 * Jun 1, 2016 </br>
 * Description of file UnitedStatesProxy.java: Just proxies from USA
 * 
 * @author rjesus
 */
public class UnitedStatesProxy extends ProxyBase implements IProxy {

	public List<HttpHost> load()
	{
		List<HttpHost> proxies = new ArrayList<HttpHost>();
		proxies.add(new HttpHost("205.202.45.195", 8080));
		proxies.add(new HttpHost("23.30.90.105", 8080));
		proxies.add(new HttpHost("23.20.66.136", 8080));
		proxies.add(new HttpHost("168.63.76.159", 3128));
		proxies.add(new HttpHost("63.141.249.37", 8089));
		proxies.add(new HttpHost("107.21.102.102", 80));
		proxies.add(new HttpHost("4.59.119.22", 443));
		proxies.add(new HttpHost("63.141.249.37", 7808));
		proxies.add(new HttpHost("205.185.122.112", 3128));
		proxies.add(new HttpHost("68.180.195.138", 80));
		proxies.add(new HttpHost("54.224.82.100", 80));
		proxies.add(new HttpHost("206.72.201.17", 3128));
		proxies.add(new HttpHost("199.115.231.51", 7808));
		proxies.add(new HttpHost("208.110.83.204", 3128));
		proxies.add(new HttpHost("107.17.100.254", 8080));
		proxies.add(new HttpHost("207.182.140.86", 3128));
		proxies.add(new HttpHost("177.71.176.187", 80));
		proxies.add(new HttpHost("100.44.124.8", 80));
		return proxies;
	}

}
