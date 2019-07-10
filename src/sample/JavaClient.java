// Based on sample Ruleset management and execution

package sample;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.ibm.json.java.JSONObject;



public class JavaClient {
	protected CloseableHttpClient m_client;
	protected HttpClientContext m_context;
	private String m_host;
	private String m_port;
	private String user;
	private String password;
	private static final String OUTPUT_FILE = "report.xml";
	
	public JavaClient(String[] args) {
		initProperties(args);
		HttpHost targetHost = new HttpHost(getServerName(), getServerPortNumber(), "http");
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()),new UsernamePasswordCredentials(user, password));

		// Create AuthCache instance
		AuthCache authCache = new BasicAuthCache();
		// Generate BASIC scheme object and add it to the local auth cache
		BasicScheme basicAuth = new BasicScheme();
		authCache.put(targetHost, basicAuth);

		// Add AuthCache to the execution context
		this.m_context = HttpClientContext.create();
		this.m_context.setCredentialsProvider(credsProvider);
		this.m_context.setAuthCache(authCache);		
		this.m_client = HttpClientBuilder.create().build(); 
	}
	
	protected String getServerName() {
		return m_host;
	}

	protected String getServerPort() {
		return m_port;
	}

	protected int getServerPortNumber() {
		return new Integer(getServerPort()).intValue();
	}
	
	public void runRESContent() {
		String urlRuleApps = getRestAuthenticatedUrl() + "/ruleapps";
		// Lists all rule applications
		// System.out.println("** Lists all the RuleApps **");
		executeGetMethod(urlRuleApps);
	}
	
	
	/*
	 * Builds the URL to the REST API with an existing authentication.
	 */
	protected String getRestAuthenticatedUrl() {
		return "http://" + getServerName() + ":" + getServerPort()
				+ "/res/apiauth";
	}
	
	protected void executeGetMethod(String url) {
		executeMethod(url, new HttpGet(url), false);
	}
	
	protected JSONObject executeMethod(String url, HttpUriRequest method,
			boolean json) {
		JSONObject responseObject = null;
		try {
			// Executes the method.
			CloseableHttpResponse response =  m_client.execute(method, m_context);
			try{
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_NO_CONTENT && statusCode != HttpStatus.SC_CREATED ) {
					System.err.println("Method failed: " + response.getStatusLine());
				}
				HttpEntity entity = response.getEntity();
				if(entity != null){
					InputStream stream = entity.getContent();
					if (json) {
						try {
							System.out.println("JSONObject.parse(stream)");
							responseObject = JSONObject.parse(stream);
						} catch (Exception e) {
							// The stream does not contain any JSON object.
							responseObject = null;
						}
					} else{
						//displayResponse(url, statusCode, getResponse(stream));
						getResponseToFile(stream);
					}
				}
			}finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} 
		return responseObject;
	}
	
	/**
	 * Redirect stream into xml file
	 */
	
	private static String getResponseToFile(InputStream stream) {
		int count = 0 ;
		ByteArrayOutputStream outputStreamToFile = new ByteArrayOutputStream() ;
		byte[] byteArray = new byte[1024];
		try {
			@SuppressWarnings("resource")
			FileOutputStream report = new FileOutputStream(OUTPUT_FILE);
			while((count = stream.read(byteArray, 0, byteArray.length)) > 0) {
				outputStreamToFile.write(byteArray, 0, count) ;
			}
			report.write(outputStreamToFile.toByteArray());
			return new String(outputStreamToFile.toByteArray(), "UTF-8");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Transform xml into html using stylesheet
	 */
	
	
	public void createHTML() {
		try {
			 TransformerFactory tFactory = TransformerFactory.newInstance();
			 Source xslDoc = new StreamSource(".\\stylesheet.xsl");
			 Source xmlDoc = new StreamSource("report.xml");
			 String outputFileName = "report.html";
			 OutputStream htmlFile = new FileOutputStream(outputFileName);
			 Transformer trasform = tFactory.newTransformer(xslDoc);
			 trasform.transform(xmlDoc, new StreamResult(htmlFile));
		} catch (Exception e)
		{
		 e.printStackTrace();
		}
		}
		
	
	/**
	 * Close HttpClient instance
	 */
	public void closeHttpClient(){
		if(m_client != null){
			try {
				m_client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Initialization of properties 
	 */
	private void initProperties(String[] args) {
		m_host = args[0];
		m_port = args[1];
		user = args[2];
		password = args[3];
		
	}

	public void usage() {
		System.out.println("Sample creating a report of the content of the RES server");
	}

	/** Main */
	public static void main(String[] args) {
		// Initialization gives credentials
		JavaClient client = new JavaClient(args);
		if (args.length == 4)
			client.usage();
		client.runRESContent();
		client.closeHttpClient();
		client.createHTML();
	}

}
