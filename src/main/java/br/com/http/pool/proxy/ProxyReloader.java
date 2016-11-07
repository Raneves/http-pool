/**
 * 
 */
package br.com.http.pool.proxy;

import static br.com.http.pool.proxy.ProxyManager.getListProxy;
import static br.com.http.pool.proxy.ProxyManager.reload;
import static br.com.http.pool.proxy.ProxyManager.setListProxy;

import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * May 31, 2016</br>
 * Description of file ProxyReloader.java: Reloads all proxies from list in ProxyUtil
 * @author rjesus
 */
public class ProxyReloader extends Thread {
	
	private static Logger LOG = Logger.getLogger(ProxyReloader.class.getName());
	
	private final int TIME_RELOAD = 240000;//4 minutes
	
	public void run() 
	{
		while(true)
		{
			try {
				sleep(TIME_RELOAD);
				setListProxy(reload());
				//LOG.log(Level.INFO, "\n\n\n\n\n\n\nProxies Updateds: "+ getListProxy().size() +"\n\n\n\n\n\n\n\n");
				System.out.println("\n\n\n\n\n\n\nProxies Updateds: "+ getListProxy().size() +"\n\n\n\n\n\n\n\n");
			}catch(Throwable e) {
				LOG.log(Level.SEVERE, "\nCritical error on ProxyReloader: " + e.getMessage(), e);
			}
		}
	}
	
	/**
	 * sleeping and log exception
	 * @param time
	 * May 31, 2016
	 * @author rjesus
	 */
	private void sleep(int time)
	{
		try 
		{
			Thread.sleep(time);
		}
		catch (InterruptedException e) 
		{
			LOG.log(Level.SEVERE, "\n\n\n\n\n\n\n\n\n\nSleeping Critical Error: " + e.getMessage() + "\n\n\n\n\n\n\n\n\n\n", e);
		}
	}

}