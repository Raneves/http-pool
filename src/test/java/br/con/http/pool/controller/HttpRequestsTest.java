package br.con.http.pool.controller;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.http.pool.controller.HttpRequests;

/**
 *
 * May 30, 2016
 * @author rjesus
 */
public class HttpRequestsTest {

	/**
	 * main class parameters
	 */
	private String url;
	private String sizeOfPool;
	private String logOption;

	/**
	 *
	 *
	 * @throws Exception
	 * Jun 2, 2016
	 * @author rjesus
	 */
	@Before
	public void setUp() throws Exception
	{
		url = "http://google.com";
		sizeOfPool = "250";
		logOption = "false";
	}

	/**
	 *
	 * @throws Exception
	 * Jun 2, 2016
	 * @author rjesus
	 */
	@After
	public void tearDown() throws Exception
	{
	}

	/**
	 * Test calls jar by main method with valid parameters
	 * May 30, 2016
	 * @author rjesus
	 */
	@Test
	public void test()
	{
		try {
			HttpRequests.main(url, sizeOfPool, logOption);
		} catch (Throwable t) {
			t.printStackTrace();
			fail("Error when try call main method: " + t.getMessage());
		}
	}

	/**
	 *
	 * May 30, 2016
	 * @author rjesus
	 */
	@Test(expected = RuntimeException.class)
	public void testWithInvalidSizePool()
	{
		HttpRequests.main(url,"invalidSize", "true");
	}
	
	/**
	 *
	 * May 30, 2016
	 * @author rjesus
	 */
	@Test(expected = RuntimeException.class)
	public void testWithInvalidURL()
	{
		HttpRequests.main("http:invalid.com", sizeOfPool, "true");
	}
	
	/**
	 *
	 * May 30, 2016
	 * @author rjesus
	 */
	@Test(expected = RuntimeException.class)
	public void testWithInvalidLogOption()
	{
		HttpRequests.main(url, sizeOfPool, "invalidOption");
	}
	
	/**
	 *
	 * May 30, 2016
	 * @author rjesus
	 */
	@Test(expected = RuntimeException.class)
	public void testWithoutLogOption()
	{
		HttpRequests.main(url, sizeOfPool);
	}
	
	/**
	 *
	 * May 30, 2016
	 * @author rjesus
	 */
	@Test(expected = RuntimeException.class)
	public void testWithInvalidMain()
	{
		HttpRequests.main();
	}
}