package com.JPMorgan.salesProccessing.Messages;

import com.JPMorgan.salesProccessing.ReportService.ReportService;
import com.JPMorgan.salesProccessing.Sales.SalesStore;
import com.JPMorgan.salesProccessing.utils.Configuration;

import java.util.List;
import java.util.logging.Logger;

/**
 * Class in charge of processing the existing messages
 *
 */
public class MessageProcessor {
	private static final Logger LOGGER = Logger.getLogger( MessageProcessor.class.getName() );

	/**
	 * Method to process the messages
	 * @param messages a list of pending messages to process
	 * @see         Message
	 */
	public static void processMessages(List<Message> messages){
		SalesStore st = new SalesStore();
		Configuration cfg = new Configuration();
		int count = 0;
		for (Message msg : messages){
			msg.recordSale(st);
			count++;
			if ((count % cfg.getMessagesBeforeReport()) == 0){
				ReportService.generateReport(st);
			}
			if ((count % cfg.getMessagesBeforeAdjustments()) == 0){
				st.performAdjustments();
			}
		}
	}
}
