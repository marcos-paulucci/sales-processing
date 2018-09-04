package com.JPMorgan.salesProccessing.Messages;

import com.JPMorgan.salesProccessing.Adjustments.adjustmentTypes.AddAdjustment;
import com.JPMorgan.salesProccessing.Adjustments.Adjustment;
import com.JPMorgan.salesProccessing.Adjustments.adjustmentTypes.MultiplyAdjustment;
import com.JPMorgan.salesProccessing.Adjustments.adjustmentTypes.SubstractAdjustment;
import com.JPMorgan.salesProccessing.Messages.types.MessageType1;
import com.JPMorgan.salesProccessing.Messages.types.MessageType2;
import com.JPMorgan.salesProccessing.Messages.types.MessageType3;
import com.JPMorgan.salesProccessing.Sales.Sale;
import com.JPMorgan.salesProccessing.utils.Configuration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;


public class MessageProducerMock {
	private static final Logger LOGGER = Logger.getLogger( MessageProducerMock.class.getName() );
	public static BigDecimal getRandomUnitPrice(){
		long leftLimit = 1L;
		long rightLimit = 100L;
		long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
		return new BigDecimal(generatedLong);
	}

	public static String getRandomProductType(){
		Configuration cfg = new Configuration();
		String[] prodTypes = cfg.getProdTypes();
		int rndProdType = new Random().nextInt(prodTypes.length);
		String randomProdType = prodTypes[rndProdType];
		return randomProdType;
	}

	public static String getProductType(int i){
		Configuration cfg = new Configuration();
		String[] prodTypes = cfg.getProdTypes();
		i = i % prodTypes.length;
		String randomProdType = prodTypes[i];
		return randomProdType;
	}

	/**
	 * generate a random number of occurrences for the second type of message
	 */
	private static long getRandomOccurrences(){
		long leftLimit = 1L;
		long rightLimit = 1000L;
		long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
		return generatedLong;
	}

	public static BigDecimal getRandomFactor(){
		return new BigDecimal(new Random().nextInt(50) + 1);
	}

	public static Message getRandomMessage(){
		Configuration cfg = new Configuration();
		int rand = new Random().nextInt(3) + 1;
		Message msg;
		String randomProdType = getRandomProductType();
		Sale sale = new Sale(randomProdType, getRandomUnitPrice());
		switch (rand) {
			case 1:
				msg = new MessageType1(sale);
				break;
			case 2:
				msg = new MessageType2(sale, getRandomOccurrences());
				break;
			case 3:
				int rand2 = new Random().nextInt(3) + 1;
				Adjustment adj;
				BigDecimal factor = getRandomFactor();
				switch (rand2) {
					case 1:
						adj = new AddAdjustment(sale.getProductType(), factor);
						break;
					case 2:
						adj = new MultiplyAdjustment(sale.getProductType(), factor);
						break;
					case 3:
						adj = new SubstractAdjustment(sale.getProductType(), factor);
						break;
					default:
						adj = new AddAdjustment(sale.getProductType(), factor);
						break;
				}
				msg = new MessageType3(sale, adj);
				break;
			default:
				msg = new MessageType1(sale);
				break;
		}
		return msg;
	}


	/**
	 * Method to generate a random number of random messages.
	 * It generates messages of the three types of existing messages in the system, with random product types loaded from config file.
	 * For the third kind of messages, it also creates random adjustment operations  with a random factor to apply.
	 * @return      list of created messages
	 * @see Message
	 */
	public static List<Message> getRandomMessages() {
		Configuration cfg = new Configuration();
		Random r = new Random();
		int min = cfg.getMessagesBeforeAdjustments();
		int max = (new Random().nextInt(10) + 1) * (min + 1);
		int numberOfMessages = r.nextInt(max - min) + min;
		LOGGER.info("Number of messages: " + String.valueOf(numberOfMessages));
		String[] prodTypes = cfg.getProdTypes();
		List<Message> messages = new ArrayList<>();
		for (int i = 0; i < numberOfMessages; i++) {
			messages.add(getRandomMessage());
		}
		return messages;
	}
}
