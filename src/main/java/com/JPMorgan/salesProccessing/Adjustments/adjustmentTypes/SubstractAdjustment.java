package com.JPMorgan.salesProccessing.Adjustments.adjustmentTypes;


import com.JPMorgan.salesProccessing.Adjustments.Adjustment;
import com.JPMorgan.salesProccessing.Sales.SaleOccurrence;

import java.math.BigDecimal;

public class SubstractAdjustment extends Adjustment {
    public SubstractAdjustment(String productType, BigDecimal factor){
        super(productType, factor);
    }
    @Override
    public void performAdjustment(SaleOccurrence sale) {
        sale.getSale().setUnitPrice(sale.getSale().getUnitPrice().subtract(this.getFactor()));
    }

    @Override
    public String getOperationstring() {
        return " - " + this.getFactor();
    }
}
