package com.JPMorgan.salesProccessing.Messages.types;

import com.JPMorgan.salesProccessing.Messages.Message;
import com.JPMorgan.salesProccessing.Sales.Sale;
import com.JPMorgan.salesProccessing.Sales.SaleOccurrence;
import com.JPMorgan.salesProccessing.Sales.SalesStore;


public class MessageType1 extends Message {
    public MessageType1(Sale sale){
        super(sale);
    }
    public void recordSale(SalesStore salesStore){
        salesStore.addSale(new SaleOccurrence(this.getSale(), new Long(1)));
    }
}
