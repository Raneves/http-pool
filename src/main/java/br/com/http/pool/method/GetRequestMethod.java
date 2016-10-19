package br.com.http.pool.method;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

/**
 *
 * Jun 1, 2016
 * Description of file GetRequestMethod.java: executes a http get request to url defined in constructor
 * @author rjesus
 */
public class GetRequestMethod extends Request {
	
	private static Logger LOG = Logger.getLogger(GetRequestMethod.class.getName());

	private final HttpContext context;

	/**
	 * @param url
	 * @param httpClient
	 * @param logOption
	 * Jun 1, 2016
	 * @author rjesus
	 */
	public GetRequestMethod(String url, HttpClient httpClient, RequestConfig defaultRequestConfig, boolean logOption) 
	{
		this.httpClient = httpClient;
		this.context = new BasicHttpContext();
		this.httpRequestBase = new HttpGet(url);
		this.logOption = logOption;
		this.defaultRequestConfig = defaultRequestConfig;
		configProtocol();
	}

	@Override
	public void run() {
		try {
			HttpResponse response = this.httpClient.execute(this.httpRequestBase, this.context);
			String responseLine = response.getStatusLine().toString(); 
			onSuccess(responseLine);
			EntityUtils.consume(response.getEntity());
			removeNotFoundProxy(responseLine);
			//successValitation(responseLine);
		} catch (Exception ex) 
		{
			this.httpRequestBase.abort();
			onError(ex.getMessage());
			//removeBadProxy(ex);
			//removeTimeOut(ex);
		}
	}

	/**
	 *
	 * @param responseLine
	 * Jun 1, 2016
	 * @author rjesus
	 */
	protected void onSuccess(String responseLine)
	{
		if(logOption)
		{
			LOG.log(Level.INFO, "message sent with success: " + responseLine);
		}
	}
	
	/**
	 *
	 * @param responseLine
	 * Jun 1, 2016
	 * @author rjesus
	 */
	protected void onError(String responseLine)
	{
		if(logOption)
		{
			LOG.log(Level.INFO, "Could not send message, error: " + responseLine);
		}
	}
}