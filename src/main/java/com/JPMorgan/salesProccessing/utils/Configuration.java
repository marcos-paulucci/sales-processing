package com.JPMorgan.salesProccessing.utils;

import com.JPMorgan.salesProccessing.Adjustments.PendingAdjustments;
import com.JPMorgan.salesProccessing.Sales.SaleOccurrence;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Configuration class to load the existing configs
 * messagesBeforeReport is the number of messages to process before printing a report on the console
 * messagesBeforeAdjustments is the number of messages to process before processing the pending adjustments to the stored sales
 * prodTypes holds a list with mock product types to perform the tests
 */
public class Configuration {

	private Integer messagesBeforeAdjustments;
	private Integer messagesBeforeReport;

	public String[] getProdTypes() {
		return prodTypes;
	}


	private String[] prodTypes;

	private Properties loadConfigFile(){
		String resourceName = "config.properties"; // could also be a constant
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties prop = new Properties();
		InputStream resourceStream = null;
		try {
			resourceStream = loader.getResourceAsStream(resourceName);
			prop.load(resourceStream);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (resourceStream != null) {
				try {
					resourceStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
	public Configuration() {
		Properties props = this.loadConfigFile();
		this.messagesBeforeReport = Integer.parseInt(props.getProperty("nLogging"));
		this.messagesBeforeAdjustments = Integer.parseInt(props.getProperty("nAdjustments"));
		this.prodTypes = props.getProperty("productTypes").split(",");
	}

	public Integer getMessagesBeforeAdjustments() {
		return messagesBeforeAdjustments;
	}

	public Integer getMessagesBeforeReport() {
		return messagesBeforeReport;
	}

}
