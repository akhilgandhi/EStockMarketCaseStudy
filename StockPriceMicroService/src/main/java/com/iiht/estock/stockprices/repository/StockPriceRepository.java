package com.iiht.estock.stockprices.repository;

import com.iiht.estock.stockprices.model.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StockPriceRepository extends JpaRepository<StockPrice, Long> {

    @Query("select s from StockPrice s where s.companyCode = ?1 and s.stockCreatedDate between ?2 and ?3")
    List<StockPrice> findAllByCompanyCodeAndStockCreatedDateBetween(Long companyCode,
                                                                    LocalDate startDate,
                                                                    LocalDate endDate);

    List<StockPrice> findAllByCompanyCode(Long companyCode);
}
