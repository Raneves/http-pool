/**
 * 
 */
package br.com.http.pool.method;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;

import br.com.http.pool.proxy.ProxyManager;

/**
 *
 * Jun 1, 2016
 * Description of file Request.java: Abstract HttpRequests class
 * @author rjesus
 */
public abstract class Request extends Thread {
	
	private static final int CONNECTION_TIMEOUT = 5000;
	
	protected HttpClient httpClient;
	protected HttpRequestBase httpRequestBase;
	protected RequestConfig defaultRequestConfig;
	protected boolean logOption;
	/**
	 *
	 * Jun 1, 2016
	 * @author rjesus
	 */
	protected void configProtocol()
	{
		// Request configuration can be overridden at the request level.
        // They will take precedence over the one set at the client level.
        RequestConfig requestConfig = RequestConfig.copy(defaultRequestConfig)
            .setSocketTimeout(CONNECTION_TIMEOUT)
            .setConnectTimeout(CONNECTION_TIMEOUT)
            .setConnectionRequestTimeout(CONNECTION_TIMEOUT)
            .setProxy(ProxyManager.retrieveNextProxy())
            .build();
        httpRequestBase.setConfig(requestConfig);
	}

	/**
	 * remove connection refused
	 * @param ex
	 * Jun 1, 2016
	 * @author rjesus
	 */
	protected void removeBadProxy(Exception ex)
	{
		if (ex.getMessage() != null && ex.getMessage().contains("refused")) {
			HttpHost proxy = (HttpHost) httpRequestBase.getConfig().getProxy();
			if (ProxyManager.getListProxy().contains(proxy)) {
				ProxyManager.getListProxy().remove(proxy);
			}
		}
	}

	/**
	 * remove connection refused
	 * @param ex
	 * Jun 1, 2016
	 * @author rjesus
	 */
	protected void removeNotFoundProxy(String response)
	{
		if (response != null && response.contains("404")) {
			HttpHost proxy = (HttpHost) httpRequestBase.getConfig().getProxy();
			if (ProxyManager.getListProxy().contains(proxy)) {
				ProxyManager.getListProxy().remove(proxy);
			}
		}
	}
	
	/**
	 * remove connection timeout
	 * @param ex
	 * Jun 1, 2016
	 * @author rjesus
	 */
	protected void removeTimeOut(Exception ex)
	{
		if (ex.getMessage() != null && ex.getMessage().contains("timed out")) 
		{
			HttpHost proxy = (HttpHost) httpRequestBase.getConfig().getProxy();
			if (ProxyManager.getListProxy().contains(proxy))
			{
				ProxyManager.getListProxy().remove(proxy);
			}
		}
	}

	/**
	 * Remove all request with error
	 * Jun 1, 2016
	 * @author rjesus
	 */
	protected void supremous()
	{
		HttpHost proxy = (HttpHost) httpRequestBase.getConfig().getProxy();
		if (ProxyManager.getListProxy().contains(proxy)) {
			ProxyManager.getListProxy().remove(proxy);
		}

	}

	/**
	 * Checks if response is ok and remove the proxy from proxyManager
	 * @param response
	 * Jun 1, 2016
	 * @author rjesus
	 */
	protected void successValitation(String response)
	{
		if (response != null && response.contains("Forbidden") || response.contains("Forbidden") || response.contains("403"))
		{
			HttpHost proxy = (HttpHost) httpRequestBase.getConfig().getProxy();
			if (ProxyManager.getListProxy().contains(proxy)) {
				ProxyManager.getListProxy().remove(proxy);
			}
		}
	}
	
	protected abstract void onSuccess(String responseLine);
	protected abstract void onError(String responseLine);
}
