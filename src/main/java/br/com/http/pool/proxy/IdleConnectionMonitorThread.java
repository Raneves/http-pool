package br.com.http.pool.proxy;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 *
 * May 31, 2016 Description of file IdleConnectionMonitorThread.java: from here http://www.baeldung.com/httpclient-connection-management</br>
 * Provides a monitor to close idle and expired connections.
 * 
 * @author rjesus
 */
public class IdleConnectionMonitorThread extends Thread {
	
	private static Logger LOG = Logger.getLogger(IdleConnectionMonitorThread.class.getName());

	private final PoolingHttpClientConnectionManager connMgr;
	private volatile boolean shutdown;

	public IdleConnectionMonitorThread(PoolingHttpClientConnectionManager connMgr)
	{
		super();
		this.connMgr = connMgr;
	}

	@Override
	public void run()
	{
		try 
		{
			while (!shutdown) 
			{
				synchronized (this) 
				{
					wait(1000);
					connMgr.closeExpiredConnections();
					connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
				}
			}
		}
		catch (Throwable e)
		{
			LOG.log(Level.SEVERE, "Critical error on IdleConnectionMonitor: " + e.getMessage(), e);
			shutdown();
		}
	}

	public void shutdown()
	{
		shutdown = true;
		synchronized (this)
		{
			notifyAll();
		}
	}
}