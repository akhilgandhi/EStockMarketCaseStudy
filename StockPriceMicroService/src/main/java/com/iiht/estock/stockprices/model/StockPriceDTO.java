package com.iiht.estock.stockprices.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class StockPriceDTO {

    private List<StockPrice> stockPrices;
    private long maxStockPrice;
    private long minStockPrice;
    private long avgStockPrice;
}
