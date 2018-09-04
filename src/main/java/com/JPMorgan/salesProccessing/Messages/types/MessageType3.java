package com.JPMorgan.salesProccessing.Messages.types;

import com.JPMorgan.salesProccessing.Adjustments.Adjustment;
import com.JPMorgan.salesProccessing.Messages.Message;
import com.JPMorgan.salesProccessing.Sales.Sale;
import com.JPMorgan.salesProccessing.Sales.SaleOccurrence;
import com.JPMorgan.salesProccessing.Sales.SalesStore;


public class MessageType3 extends Message {

    private Adjustment adjustment;

    public MessageType3(Sale sale, Adjustment adj){
        super(sale);
        this.adjustment = adj;
    }


    public void recordSale(SalesStore salesStore){

        salesStore.addSale(new SaleOccurrence(this.getSale(), new Long(1)));
        this.enqueueAdjustment(salesStore);
    }

    private void enqueueAdjustment(SalesStore salesStore){
        salesStore.getPendingAdjustments().addAdjustment(this.adjustment);
    }

    public Adjustment getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(Adjustment adjustment) {
        this.adjustment = adjustment;
    }
}
