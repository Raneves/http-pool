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
 * May 31, 2016
 * Description of file BrProxy.java: Just proxies from brazil.
 * @author rjesus
 */
public class BrProxy extends ProxyBase implements IProxy {

	public List<HttpHost> load() {
		List<HttpHost> proxies = new ArrayList<HttpHost>();
		proxies.add(new HttpHost("177.75.186.11", 8080));
		proxies.add(new HttpHost("187.63.15.61", 3128));
		proxies.add(new HttpHost("187.28.188.146", 3128));
		proxies.add(new HttpHost("189.84.210.2", 3128));
		proxies.add(new HttpHost("187.111.15.221", 8084));
		proxies.add(new HttpHost("189.58.100.98", 8080));
		proxies.add(new HttpHost("177.220.130.12", 80));
		proxies.add(new HttpHost("187.60.222.70", 3128));
		proxies.add(new HttpHost("187.109.57.246", 80));
		proxies.add(new HttpHost("200.155.164.246", 3128));
		proxies.add(new HttpHost("177.103.218.184", 3128));
		proxies.add(new HttpHost("200.192.250.145", 8081));
		proxies.add(new HttpHost("201.76.172.110", 8080));
		proxies.add(new HttpHost("186.226.48.16", 3128));
		proxies.add(new HttpHost("187.84.241.1", 8080));
		proxies.add(new HttpHost("187.6.254.26", 80));
		proxies.add(new HttpHost("187.109.57.246", 8080));
		proxies.add(new HttpHost("189.42.193.140", 1080));
		proxies.add(new HttpHost("201.85.50.249", 80));
		proxies.add(new HttpHost("189.3.110.134", 3128));
		proxies.add(new HttpHost("177.75.186.11", 8080));
		proxies.add(new HttpHost("189.126.198.3", 8080));
		proxies.add(new HttpHost("177.44.7.251", 8080));
		proxies.add(new HttpHost("189.84.210.1", 3128));
		proxies.add(new HttpHost("187.73.108.65", 8080));
		proxies.add(new HttpHost("189.115.138.217", 3128));
		
		proxies.add(new HttpHost("200.153.150.142", 3128));
		proxies.add(new HttpHost("201.12.116.18", 8080));
		proxies.add(new HttpHost("189.114.75.22", 8080));
		proxies.add(new HttpHost("187.111.15.221", 8084));
		proxies.add(new HttpHost("186.194.47.107", 8080));
		proxies.add(new HttpHost("187.75.157.163", 8080));
		proxies.add(new HttpHost("187.115.83.151", 80));
		proxies.add(new HttpHost("189.113.64.125", 8080));
		
		proxies.add(new HttpHost("187.73.99.21", 8080));
		proxies.add(new HttpHost("201.80.244.111", 3128));
		proxies.add(new HttpHost("186.219.25.230", 3128));
		proxies.add(new HttpHost("200.178.252.18", 8080));
		proxies.add(new HttpHost("177.21.233.25", 8080));
		proxies.add(new HttpHost("189.11.198.36", 8080));
		
		
		return proxies;
	}

}