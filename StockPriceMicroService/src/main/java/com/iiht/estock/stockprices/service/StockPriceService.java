package com.iiht.estock.stockprices.service;

import com.iiht.estock.stockprices.exception.StockPriceNotCreatedException;
import com.iiht.estock.stockprices.exception.StockPriceNotFoundException;
import com.iiht.estock.stockprices.model.StockPrice;

import java.time.LocalDate;
import java.util.List;

public interface StockPriceService {
    StockPrice addStockPrice(StockPrice stockPrice) throws StockPriceNotCreatedException;

    List<StockPrice> getAllStockPricesByCompanyCodeBetweenDates(Long companyCode,
                                                                LocalDate startDate,
                                                                LocalDate endDate) throws StockPriceNotFoundException;

    List<StockPrice> getAllStockPricesByCompanyCode(Long companyCode) throws StockPriceNotFoundException;

    boolean deleteAll(Long companyCode);
}
