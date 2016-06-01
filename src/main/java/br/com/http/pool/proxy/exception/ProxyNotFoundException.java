package br.com.http.pool.proxy.exception;

import org.jsoup.nodes.Document;

/**
 *
 * May 31, 2016
 * Description of file ProxyNotFoundException.java: Exception throws when the IProxy implementation don't have a valid proxy 
 * @author rjesus
 */
public class ProxyNotFoundException extends Exception {
	
	private static final long serialVersionUID = -1038428496017090096L;

	public ProxyNotFoundException(Document doc) 
	{
		super("Without proxy loaded from: " + doc.baseUri());
	}
}