package com.JPMorgan.salesProccessing.Messages;

import com.JPMorgan.salesProccessing.Sales.Sale;
import com.JPMorgan.salesProccessing.Sales.SalesStore;

public abstract class Message {
    private Sale sale;

    /**
     * Message constructor
     *
     * @param  sale  an instance of a sale
     * @see         Sale
     */
    public Message(Sale sale){
        this.sale = sale;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    /**
     * Method to record the sale in memory
     *
     * @param  salesStore  an instance of SalesStore
     * @see         SalesStore
     */
    public abstract void recordSale(SalesStore salesStore);
}
