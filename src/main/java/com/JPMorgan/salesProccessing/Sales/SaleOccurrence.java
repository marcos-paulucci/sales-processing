package com.JPMorgan.salesProccessing.Sales;

/**
 * SaleOccurrence class, wraps Sale class and adds an occurrence
 */
public class SaleOccurrence {
    private Sale sale;
    private Long occurrences;

    /**
     * constructor
     *
     * @param  sale  an instance of a sale
     * @param  occurrences of this sale
     * @see         Sale
     */
    public SaleOccurrence(Sale sale, Long occurrences){
        this.sale = sale;
        this.occurrences = occurrences;
    }

    public Long getOccurrences() {
        return occurrences;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public String getProductType(){
        return this.sale.getProductType();
    }
}
