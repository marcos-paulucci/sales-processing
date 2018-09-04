package com.JPMorgan.salesProccessing.Adjustments;

import com.JPMorgan.salesProccessing.Sales.SaleOccurrence;

import java.math.BigDecimal;

public abstract class Adjustment {

    /**
     * Adjustment constructor
     *
     * @param  productType  a String referring to the product type
     * @param  factor a BigDecimal holding the value for the operation
     */
    public Adjustment(String productType, BigDecimal factor){
        this.factor = factor;
        this.productType = productType;
    }
    private BigDecimal factor;

    public String getProductType() {
        return productType;
    }

    private String productType;

    /**
     * Abstract method to be implemented by the subclasses
     *
     * @param  sale  an instance of SaleOccurrence
     * @see         SaleOccurrence
     */
    public abstract void performAdjustment(SaleOccurrence sale);

    public abstract String getOperationstring();

    public BigDecimal getFactor() {
        return factor;
    }

}
