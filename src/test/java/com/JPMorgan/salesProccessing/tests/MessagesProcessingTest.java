package com.JPMorgan.salesProccessing.tests;

import com.JPMorgan.salesProccessing.Adjustments.Adjustment;
import com.JPMorgan.salesProccessing.Adjustments.adjustmentTypes.AddAdjustment;
import com.JPMorgan.salesProccessing.Adjustments.adjustmentTypes.MultiplyAdjustment;
import com.JPMorgan.salesProccessing.Adjustments.adjustmentTypes.SubstractAdjustment;
import com.JPMorgan.salesProccessing.Messages.MessageProducerMock;
import com.JPMorgan.salesProccessing.Messages.MessageProcessor;
import com.JPMorgan.salesProccessing.Messages.Message;
import com.JPMorgan.salesProccessing.ReportService.ReportService;
import com.JPMorgan.salesProccessing.Sales.Sale;
import com.JPMorgan.salesProccessing.Sales.SaleOccurrence;
import com.JPMorgan.salesProccessing.Sales.SalesStore;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;



/**
 * Test suite for testing message processing
 */
public class MessagesProcessingTest {
	private static final Logger LOGGER = Logger.getLogger(MessagesProcessingTest.class.getName());
	private Sale s;
	private SaleOccurrence so;
	private SalesStore salesStore;

	@Before
	public void init() throws Exception {
		s = new Sale(MessageProducerMock.getRandomProductType(), MessageProducerMock.getRandomUnitPrice());
		so = new SaleOccurrence(s,new Long(1));

	}

	/**
	 * test add adjustment
	 */
	@Test
	public void testAddAdjustment() throws Exception {

		Adjustment adj = new AddAdjustment(s.getProductType(), MessageProducerMock.getRandomFactor());
		BigDecimal rightVal = so.getSale().getUnitPrice().add(adj.getFactor());
		adj.performAdjustment(so);
		Assert.assertEquals(rightVal, so.getSale().getUnitPrice());
	}

	/**
	 * test multiply adjustment
	 */
	@Test
	public void testMultiplyAdjustment() throws Exception {
		Adjustment adj = new MultiplyAdjustment(s.getProductType(), MessageProducerMock.getRandomFactor());
		BigDecimal rightVal = so.getSale().getUnitPrice().multiply(adj.getFactor());
		adj.performAdjustment(so);
		Assert.assertEquals(rightVal, so.getSale().getUnitPrice());
	}

	/**
	 * test substract adjustment
	 */
	@Test
	public void testSubstractAdjustment() throws Exception {
		Adjustment adj = new SubstractAdjustment(s.getProductType(), MessageProducerMock.getRandomFactor());
		BigDecimal rightVal = so.getSale().getUnitPrice().subtract(adj.getFactor());
		adj.performAdjustment(so);
		Assert.assertEquals(rightVal, so.getSale().getUnitPrice());
	}

	/**
	 * test report calculation
	 */
	@Test
	public void testReportCalculation() throws Exception {
		String prodType = MessageProducerMock.getProductType(1);
		Sale s1 = new Sale(prodType, MessageProducerMock.getRandomUnitPrice());
		SaleOccurrence so1 = new SaleOccurrence(s1,new Long(1));
		Sale s2 = new Sale(prodType, MessageProducerMock.getRandomUnitPrice());
		SaleOccurrence so2 = new SaleOccurrence(s2,new Long(2));
		Sale s3 = new Sale(prodType, MessageProducerMock.getRandomUnitPrice());
		SaleOccurrence so3 = new SaleOccurrence(s3,new Long(5));
		salesStore = new SalesStore();
		salesStore.addSale(so1);
		salesStore.addSale(so2);
		salesStore.addSale(so3);

		BigDecimal total = new BigDecimal(0);
		int numberOfSales = 0;

		numberOfSales  += so1.getOccurrences();
		BigDecimal unitPrice = so1.getSale().getUnitPrice();
		long nSales = so1.getOccurrences().longValue();
		BigDecimal bd = new BigDecimal(nSales);
		total = total.add(unitPrice.multiply(bd));

		numberOfSales  += so2.getOccurrences();
		unitPrice = so2.getSale().getUnitPrice();
		nSales = so2.getOccurrences().longValue();
		bd = new BigDecimal(nSales);
		total = total.add(unitPrice.multiply(bd));

		numberOfSales  += so3.getOccurrences();
		unitPrice = so3.getSale().getUnitPrice();
		nSales = so3.getOccurrences().longValue();
		bd = new BigDecimal(nSales);
		total = total.add(unitPrice.multiply(bd));

		HashMap<String, Pair<String,String>> reportResult = ReportService.generateReport(salesStore);
		String numSalesReport = reportResult.get(prodType).getKey();
		String totalReport = reportResult.get(prodType).getValue();
		Assert.assertEquals(String.valueOf(numberOfSales), numSalesReport);
		Assert.assertEquals(String.valueOf(total), totalReport);
	}

	/**
	 * Get random messages and test processing
	 */
	@Test
	public void testBasic() throws Exception {
		MessageProcessor.processMessages(MessageProducerMock.getRandomMessages());
	}
}