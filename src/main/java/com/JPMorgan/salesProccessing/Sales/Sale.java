package com.JPMorgan.salesProccessing.Sales;

import java.math.BigDecimal;

/**
 * Sale class, with its product type and unit price
 */
public class Sale {
    private String productType;
    private BigDecimal unitPrice;

    /**
     * constructor
     *
     * @param  productType  a String referring to the product type
     * @param  unitPrice a BigDecimal holding the price for one unit of the product for this sale
     */
    public Sale(String productType, BigDecimal unitPrice){
        this.productType = productType;
        this.unitPrice = unitPrice;
    }

    public String getProductType() {
        return productType;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
