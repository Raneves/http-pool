package br.com.http.pool.method;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class PostRequestMethod extends Request {
	
	private final HttpContext context;
	private static Logger LOG = Logger.getLogger(GetRequestMethod.class.getName());

	/**
	 * @param url
	 * @param httpClient
	 * @param logOption
	 * Jun 1, 2016
	 * @author rjesus
	 */
	public PostRequestMethod(String url, HttpClient httpClient, RequestConfig defaultRequestConfig, boolean logOption) 
	{
		this.httpClient = httpClient;
		this.context = new BasicHttpContext();
		this.httpRequestBase = new HttpPost(url);
		this.logOption = logOption;
		this.defaultRequestConfig = defaultRequestConfig;
		configProtocol();
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	      	nameValuePairs.add(new BasicNameValuePair("username", "_Born"));
	        nameValuePairs.add(new BasicNameValuePair("password", "asdfdgrewf"));
	        nameValuePairs.add(new BasicNameValuePair("redirect", "index.php"));
	        nameValuePairs.add(new BasicNameValuePair("sid", "2d83703981843cf15ac40170a57daac4"));
	        nameValuePairs.add(new BasicNameValuePair("login", "Entrar"));
	        ((HttpPost)httpRequestBase).setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			HttpResponse response = this.httpClient.execute(this.httpRequestBase, this.context);
			//System.out.println(EntityUtils.toString(response.getEntity()));
			String responseLine = response.getStatusLine().toString(); 
			onSuccess(responseLine);
			EntityUtils.consume(response.getEntity());
			successValitation(responseLine);
		} catch (Exception ex) {
			this.httpRequestBase.abort();
			onError(ex.getMessage());
			//removeBadProxy(ex);
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