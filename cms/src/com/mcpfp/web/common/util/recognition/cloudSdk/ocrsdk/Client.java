package com.mcpfp.web.common.util.recognition.cloudSdk.ocrsdk;

import java.io.*;
import java.net.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Client {
	public String applicationId;
	public String password;

	public String serverUrl = "http://cloud.ocrsdk.com";

	public Task processImage(String filePath, ProcessingSettings settings) throws Exception {
		URL url = new URL(serverUrl + "/processImage?" + settings.asUrlParams());
		return postFileToUrl(filePath, url);
	}
	
	public Task getTaskStatus(String taskId) throws Exception {
		URL url = new URL(serverUrl + "/getTaskStatus?taskId=" + taskId);

		HttpURLConnection connection = openGetConnection(url);
		return getResponse(connection);
	}
	
	private HttpURLConnection openGetConnection(URL url) throws Exception {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		// connection.setRequestMethod("GET");
		setupAuthorization(connection);
		return connection;
	}
	
	public String downloadResult(Task task) throws Exception {
		StringBuilder result = new StringBuilder();
		if (task.Status != Task.TaskStatus.Completed) {
			throw new IllegalArgumentException("Invalid task status");
		}

		if (task.DownloadUrl == null) {
			throw new IllegalArgumentException("Cannot download result without url");
		}

		URL url = new URL(task.DownloadUrl);
		URLConnection connection = url.openConnection(); // do not use authenticated connection
		
		//读取字符流
		InputStream inputStream = connection.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String line = null;
		
		while ((line = bufferedReader.readLine()) != null) {
			result.append(line);
		}
		
		return result.toString();
	}
	
	private byte[] readDataFromFile(String filePath) throws Exception {
		File file = new File(filePath);
		long fileLength = file.length();
		byte[] dataBuffer = new byte[(int) fileLength];

		InputStream inputStream = new FileInputStream(file);
		try {

			int offset = 0;
			int numRead = 0;
			while (true) {
				if (offset >= dataBuffer.length) {
					break;
				}
				numRead = inputStream.read(dataBuffer, offset, dataBuffer.length - offset);
				if (numRead < 0) {
					break;
				}
				offset += numRead;
			}
			if (offset < dataBuffer.length) {
				throw new IOException("Could not completely read file "
						+ file.getName());
			}
		} finally {
			inputStream.close();
		}
		return dataBuffer;
	}
	
	private Task postFileToUrl(String filePath, URL url) throws Exception {
		byte[] fileContents = readDataFromFile(filePath);

		HttpURLConnection connection = openPostConnection(url);
		connection.setRequestProperty("Content-Length", Integer.toString(fileContents.length));

		OutputStream stream = connection.getOutputStream();
		try {
			stream.write(fileContents);
		} finally {
			stream.close();
		}

		return getResponse(connection);
	}
	
	private HttpURLConnection openPostConnection(URL url) throws Exception {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		setupAuthorization(connection);
		connection.setRequestProperty("Content-Type", "applicaton/octet-stream");

		return connection;
	}
	
	private void setupAuthorization(URLConnection connection) {
		String authString = "Basic: " + encodeUserPassword();
		authString = authString.replaceAll("\n", "");
		connection.addRequestProperty("Authorization", authString);
	}
	
	private String encodeUserPassword() {
		String toEncode = applicationId + ":" + password;
		return Base64.encode(toEncode);
	}
	
	/**
	 * Read server response from HTTP connection and return task description.
	 * 
	 * @throws Exception
	 *             in case of error
	 */
	private Task getResponse(HttpURLConnection connection) throws Exception {
		int responseCode = connection.getResponseCode();
		if (responseCode == 200) {
			InputStream inputStream = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			return new Task(reader);
		} else if (responseCode == 401) {
			throw new Exception(
					"HTTP 401 Unauthorized. Please check your application id and password");
		} else if (responseCode == 407) {
			throw new Exception("HTTP 407. Proxy authentication error");
		} else {
			String message = "";
			try {
				InputStream errorStream = connection.getErrorStream();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(errorStream));

				// Parse xml error response
				InputSource source = new InputSource();
				source.setCharacterStream(reader);
				DocumentBuilder builder = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder();
				Document doc = builder.parse(source);
				
				NodeList error = doc.getElementsByTagName("error");
				Element err = (Element) error.item(0);
				
				message = err.getTextContent();
			} catch (Exception e) {
				throw new Exception("Error getting server response");
			}

			throw new Exception("Error: " + message);
		}
	}
	
}