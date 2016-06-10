package br.con.http.pool.proxy;

import java.util.List;

import org.apache.http.HttpHost;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.http.pool.proxy.impl.DailyProxy;

public class DailyProxyTest {

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
		DailyProxy dailyProxy = new DailyProxy();
		List<HttpHost> proxies = dailyProxy.load();
		Assert.assertTrue("List of dailyProxies is null or empty", proxies != null && !proxies.isEmpty());
	}

}
