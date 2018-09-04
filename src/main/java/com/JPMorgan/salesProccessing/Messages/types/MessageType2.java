package com.JPMorgan.salesProccessing.Messages.types;

import com.JPMorgan.salesProccessing.Messages.Message;
import com.JPMorgan.salesProccessing.Sales.Sale;
import com.JPMorgan.salesProccessing.Sales.SaleOccurrence;
import com.JPMorgan.salesProccessing.Sales.SalesStore;


public class MessageType2 extends Message {
    public MessageType2(Sale sale, Long occurrences){
        super(sale);
        this.occurrences = occurrences;
    }
    private Long occurrences;
    public void recordSale(SalesStore salesStore){
        salesStore.addSale(new SaleOccurrence(this.getSale(), occurrences));
    }
}
