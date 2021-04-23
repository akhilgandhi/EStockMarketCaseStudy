package com.iiht.estock.stockprices.service;

import com.iiht.estock.stockprices.exception.StockPriceNotCreatedException;
import com.iiht.estock.stockprices.exception.StockPriceNotFoundException;
import com.iiht.estock.stockprices.model.Company;
import com.iiht.estock.stockprices.model.StockPrice;
import com.iiht.estock.stockprices.repository.StockPriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class StockPriceServiceImpl implements StockPriceService {

    private final StockPriceRepository stockPriceRepository;
    private final RestTemplate restTemplate;

    @Override
    public StockPrice addStockPrice(StockPrice stockPrice) throws StockPriceNotCreatedException {

        try {
            stockPrice.setStockCreatedDate(LocalDate.now());
            Company company = restTemplate.getForEntity("http://company-service:8089/api/v1.0/market/company/info/" + stockPrice.getCompanyCode(), Company.class).getBody();
            if (company != null)
                return stockPriceRepository.save(stockPrice);
            else
                throw new StockPriceNotCreatedException("Company " + stockPrice.getCompanyCode() + " not found.");
        } catch (Exception e) {
            log.error(e.getClass() + " -- " + e.getMessage());
            String message = e.getMessage().substring(e.getMessage().indexOf("message") + 10, e.getMessage().indexOf('.'));
            throw new StockPriceNotCreatedException(message);
        }
    }

    @Override
    public List<StockPrice> getAllStockPricesByCompanyCodeBetweenDates(Long companyCode,
                                                                       LocalDate startDate,
                                                                       LocalDate endDate) throws StockPriceNotFoundException {
        try {
            return stockPriceRepository.findAllByCompanyCodeAndStockCreatedDateBetween(companyCode,
                    startDate, endDate);
        } catch (Exception e) {
            log.error(e.getClass() + " -- " + e.getMessage());
            throw new StockPriceNotFoundException(e.getMessage());
        }
    }

    @Override
    public List<StockPrice> getAllStockPricesByCompanyCode(Long companyCode) throws StockPriceNotFoundException {
        try {
            return stockPriceRepository.findAllByCompanyCode(companyCode);
        } catch (Exception e) {
            log.error(e.getClass() + " -- " + e.getMessage());
            throw new StockPriceNotFoundException(e.getMessage());
        }
    }

    @Override
    public boolean deleteAll(Long companyCode) {
        List<StockPrice> stockPrices = stockPriceRepository.findAllByCompanyCode(companyCode);
        stockPriceRepository.deleteAll(stockPrices);
        if (stockPriceRepository.findAllByCompanyCode(companyCode).isEmpty())
            return true;
        else return false;
    }
}
