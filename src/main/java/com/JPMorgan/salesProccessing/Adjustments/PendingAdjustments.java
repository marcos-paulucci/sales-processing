package com.JPMorgan.salesProccessing.Adjustments;

import com.JPMorgan.salesProccessing.Sales.SaleOccurrence;
import com.JPMorgan.salesProccessing.Sales.SalesStore;
import com.JPMorgan.salesProccessing.utils.StringUtils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

/**
 * Class that keeps the pending adjustments/operations to later perform over the existing sales
 */
public class PendingAdjustments {
    private static final Logger LOGGER = Logger.getLogger( PendingAdjustments.class.getName() );
    private Queue<Adjustment> adjustments;
    public PendingAdjustments (){
        this.adjustments = new LinkedList<>();
    }
    /**
     * Method to enqueue an adjustment for later processing
     * @param adjustment to enqueue
     * @see Adjustment
     */
    public void addAdjustment(Adjustment adjustment){
        this.adjustments.add(adjustment);
    }

    /**
     * Method to execute the enqueued adjustments. After all adjustments are executed, the queue is cleared
     * @param salesStore holds the store where all sales reside
     * @see SalesStore
     */
    public void performAdjustments(SalesStore salesStore){
        LOGGER.info("performing " + adjustments.size() +  " adjustments");
        StringBuilder stb = new StringBuilder("\n");
        for(Adjustment adj : adjustments){
            stb.append("performing adjustment, prodType: " + StringUtils.padRight(adj.getProductType()) + ", adjustment: " + adj.getOperationstring() + ".\n");
            for(SaleOccurrence saleOc : salesStore.getProductSales().get(adj.getProductType())){
                adj.performAdjustment(saleOc);
            }
        }
        adjustments.clear();
        LOGGER.info(stb.toString());
    }
}
