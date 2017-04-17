package com.cms.web.common.util.recognition.cloudSdk;

/**
 * Authentication settings for Cloud OCR SDK client.
 * 
 * Please provide your application id and password here.
 */
public class ClientSettings {
/*	// Name of application you created
	public static final String APPLICATION_ID = "ZSL_OCR_PRJ";
	// Password should be sent to your e-mail after application was created
	public static final String PASSWORD = "yTgKx0LjuJvONdI5pUy+F8df";*/
	
/*	// Name of application you created
	public static final String APPLICATION_ID = "zhaosuliao";
	// Password should be sent to your e-mail after application was created
	public static final String PASSWORD = "Sv2fqgQUtaN3Iw6gs74PLKSi";*/
	
	// Name of application you created
	public static final String APPLICATION_ID = "ACC_OCR_PRJ";
	// Password should be sent to your e-mail after application was created
	public static final String PASSWORD = "htQLaXqa/492UKjyiNruQnhw";
	
	public static void setupProxy()
	{
		// Uncomment this if you are behind a proxy
		/*
		String host = "";
		String port = "";
		final String user = "";
		final String password = "";

		Authenticator.setDefault(
			new Authenticator() {
		 		public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication( user, password.toCharArray());
				}
 			}
		);
		
		System.getProperties().put("http.proxyHost", host );
		System.getProperties().put("https.proxyHost", host );
		System.getProperties().put("http.proxyPort", port);
		System.getProperties().put("https.proxyPort", port);
		System.getProperties().put("http.proxyUser", user);
		System.getProperties().put("https.proxyUser", user);
		System.getProperties().put("http.proxyPassword", password);
		System.getProperties().put("https.proxyPassword", password);
		*/
	}
}
