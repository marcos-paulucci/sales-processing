package com.JPMorgan.salesProccessing.ReportService;

import com.JPMorgan.salesProccessing.Sales.SaleOccurrence;
import com.JPMorgan.salesProccessing.Sales.SalesStore;
import com.JPMorgan.salesProccessing.utils.StringUtils;
import javafx.util.Pair;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


/**
 * Class with the logic to log a report detailing the number of sales of each product and their total value
 */
public class ReportService {
    private static final Logger LOGGER = Logger.getLogger( ReportService.class.getName() );

    /**
     * Method to generate the report
     *
     * @param  salesStore  the instance of the sales stor
     * @see         SalesStore
     */
    public static HashMap<String, Pair<String,String>> generateReport(SalesStore salesStore){
        StringBuilder stb = new StringBuilder("\nThe application is pausing to perform some updates");
        HashMap<String, Pair<String,String>> result = new HashMap<>();
        for (Map.Entry<String, List<SaleOccurrence>> entry : salesStore.getProductSales().entrySet()) {
            String productType = entry.getKey();
            Long numberOfSales = new Long(0);
            BigDecimal total = new BigDecimal(0);
            List<SaleOccurrence> sales = entry.getValue();
            for (SaleOccurrence sale : sales){
                numberOfSales += sale.getOccurrences();
                BigDecimal unitPrice = sale.getSale().getUnitPrice();
                long nSales = sale.getOccurrences().longValue();
                BigDecimal bd = new BigDecimal(nSales);
                total = total.add(unitPrice.multiply(bd));
            }
            result.put(productType, new Pair(numberOfSales.toString(), total.toString()));
            stb.append("Product:  " + StringUtils.padRight(productType)+ ", number of sales: " + numberOfSales.toString() + ", total: " + total.toString() + "£.\n");
        }
        LOGGER.info(stb.toString());
        return result;
    }
}
