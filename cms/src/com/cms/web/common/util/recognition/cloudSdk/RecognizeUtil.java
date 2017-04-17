package com.cms.web.common.util.recognition.cloudSdk;

import com.cms.web.common.util.recognition.cloudSdk.ocrsdk.Client;
import com.cms.web.common.util.recognition.cloudSdk.ocrsdk.ProcessingSettings;
import com.cms.web.common.util.recognition.cloudSdk.ocrsdk.Task;
import com.cms.web.common.util.recognition.cloudSdk.ocrsdk.ProcessingSettings.OutputFormat;

public class RecognizeUtil {
	private Client restClient;
	
	/**
	 * start to recognize image.
	 */
	public String recognizeStart(String filePath) throws Exception{
		String[] args = new String[3];
		args[0] = filePath;
		args[1] = "result.txt";
		args[2] = "--lang=ChinesePRC,English";
		
		if (!checkAppId()) {
			return "";
		}
		
		if(null == filePath || "".equals(filePath)){
			System.out.println("No files to process.");
			return "";
		}
		
		//代理设置
		//ClientSettings.setupProxy();
		
		restClient = new Client();
		// replace with 'https://cloud.ocrsdk.com' to enable secure connection
		restClient.serverUrl = "http://cloud.ocrsdk.com";
		restClient.applicationId = ClientSettings.APPLICATION_ID;
		restClient.password = ClientSettings.PASSWORD;
		
		return performRecognition(filePath);
	}
	
	/**
	 * Parse command line and recognize one or more documents.
	 */
	private String performRecognition(String filePath)throws Exception {
		ProcessingSettings settings = new ProcessingSettings();
		settings.setLanguage("ChinesePRC,English");
		settings.setOutputFormat(OutputFormat.txt);
		
		System.out.println("Uploading file..");
		Task task = restClient.processImage(filePath, settings);
		
		return waitAndDownloadResult(task);
	}
	
	/**
	 * Wait until task processing finishes and download result.
	 */
	private String waitAndDownloadResult(Task task) throws Exception {
		task = waitForCompletion(task);
		String outputContent = "";
		
		if (task.Status == Task.TaskStatus.Completed) {
			System.out.println("Downloading..");
			outputContent = restClient.downloadResult(task);
			System.out.println("Ready");
		} else if (task.Status == Task.TaskStatus.NotEnoughCredits) {
			System.out.println("Not enough credits to process document. Please add more pages to your application's account.");
		} else {
			System.out.println("Task failed");
		}
		
		return outputContent;
	}
	
	/** 
	 * Wait until task processing finishes
	 */
	private Task waitForCompletion(Task task) throws Exception {
		// Note: it's recommended that your application waits
		// at least 2 seconds before making the first getTaskStatus request
		// and also between such requests for the same task.
		// Making requests more often will not improve your application performance.
		// Note: if your application queues several files and waits for them
		// it's recommended that you use listFinishedTasks instead (which is described
		// at http://ocrsdk.com/documentation/apireference/listFinishedTasks/).
		while (task.isTaskActive()) {

			Thread.sleep(1000);
			System.out.println("Waiting..");
			task = restClient.getTaskStatus(task.Id);
		}
		return task;
	}
	
	/**
	 * Check that user specified application id and password.
	 * 
	 * @return false if no application id or password
	 */
	private static boolean checkAppId() {
		String appId = ClientSettings.APPLICATION_ID;
		String password = ClientSettings.PASSWORD;
		if (appId.isEmpty() || password.isEmpty()) {
			System.out.println("Error: No application id and password are specified.");
			System.out.println("Please specify them in ClientSettings.java.");
			return false;
		}
		return true;
	}
	
}
