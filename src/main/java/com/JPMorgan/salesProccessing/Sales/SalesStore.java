package com.JPMorgan.salesProccessing.Sales;

import com.JPMorgan.salesProccessing.Adjustments.PendingAdjustments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 * SalesStore class, holding the sales and the pending adjustments
 * @see SaleOccurrence,PendingAdjustments
 */
public class SalesStore {
    private HashMap<String, List<SaleOccurrence>> productSales;
    private PendingAdjustments pendingAdjustments;
    public SalesStore(){
        productSales = new HashMap<>();
        pendingAdjustments = new PendingAdjustments();
    }

    /**
     * Method to store a saleOccurrence
     *
     * @param  saleOccurrence  an instance of SaleOccurrence
     * @see         SaleOccurrence
     */
    public void addSale(SaleOccurrence saleOccurrence){
        //if there was already a sale of this product, add the sale to the existing list of sales
        if (productSales.containsKey(saleOccurrence.getProductType())){
            List<SaleOccurrence> sales = productSales.get(saleOccurrence.getProductType());
            sales.add(saleOccurrence);
        } else {
            //There was no sale for the product, we need to create the list of sales and store it
            List<SaleOccurrence> sales = new ArrayList<>();
            sales.add(saleOccurrence);
            productSales.put(saleOccurrence.getProductType(), sales);
        }
    }

    public HashMap<String, List<SaleOccurrence>> getProductSales() {
        return productSales;
    }


    public PendingAdjustments getPendingAdjustments() {
        return pendingAdjustments;
    }

    /**
     * Method to perform the pending adjustments. Delegates the task to the pendingAdjustments private instance
     *
     * @see         PendingAdjustments
     */
    public void performAdjustments(){
        this.pendingAdjustments.performAdjustments(this);
    }
}
