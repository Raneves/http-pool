package br.con.http.pool.proxy;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpHost;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.http.pool.proxy.ProxyManager;

public class ProxyManagerTest {

	private static Logger LOG = Logger.getLogger(ProxyManagerTest.class.getName());

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void test()
	{
		Set<HttpHost> proxies =  ProxyManager.getListProxy();
		Assert.assertTrue("list of proxies could not be null or empty", proxies != null && !proxies.isEmpty());
		int fastListIterate = 0;
		
		do
		{
			HttpHost proxy  = ProxyManager.retrieveNextProxy();
			Assert.assertNotNull("proxy could not be null", proxy);
			LOG.log(Level.INFO, "Proxy: " + proxy.getHostName() + " and port: " + proxy.getPort());
			fastListIterate++;
		} while(proxies.size() > fastListIterate);
		
		LOG.log(Level.INFO, "Total Proxies: " + proxies.size());
	}
}