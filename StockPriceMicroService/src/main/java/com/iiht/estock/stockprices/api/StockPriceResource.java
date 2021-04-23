package com.iiht.estock.stockprices.api;

import com.iiht.estock.stockprices.exception.ExceptionResponseMessage;
import com.iiht.estock.stockprices.exception.StockPriceNotCreatedException;
import com.iiht.estock.stockprices.exception.StockPriceNotFoundException;
import com.iiht.estock.stockprices.model.StockPrice;
import com.iiht.estock.stockprices.model.StockPriceDTO;
import com.iiht.estock.stockprices.service.StockPriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1.0/market/stock")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "StockPrice", description = "Stock Price Resource")
public class StockPriceResource {

    private final StockPriceService stockPriceService;

    /**
     * Add a {@link StockPrice} based on company code
     * @param companyCode
     * @param stockPrice
     * @return
     */
    @PostMapping(value = "/add/{companycode}")
    @Operation(summary = "Add stock price by Company Code", description = "", tags = { "StockPrice" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Successful Operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = StockPrice.class)))),
            @ApiResponse(responseCode = "500",
                    description = "Stock price not created",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponseMessage.class))))
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public StockPrice addStockPriceByCompanyCode(@Parameter(description = "Company Code", required = true)
                                                     @PathVariable("companycode") Long companyCode,
                                                 @Parameter(description="Stock Price to add.",
                                                         required=true, schema=@Schema(implementation = StockPrice.class))
                                                 @RequestBody StockPrice stockPrice) throws StockPriceNotCreatedException {

        try {
            stockPrice.setCompanyCode(companyCode);
            return stockPriceService.addStockPrice(stockPrice);
        } catch (StockPriceNotCreatedException e) {
            log.error(e.getClass().getName() + " -- " + e.getMessage());
            throw new StockPriceNotCreatedException(e.getMessage());
        }
    }

    @Operation(summary = "Get stock price by company code between date range", description = "", tags = { "StockPrice" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successful Operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = StockPriceDTO.class)))),
            @ApiResponse(responseCode = "404",
                    description = "Stock price Not Found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExceptionResponseMessage.class))))
    })
    @GetMapping(value = "/get/{companycode}/{startdate}/{enddate}")
    public StockPriceDTO getStockByCompanyBetweenDates(@Parameter(description = "Company Code", required = true)
                                                           @PathVariable("companycode") Long companyCode,
                                                       @Parameter(description = "Start Date", required = true)
                                                       @PathVariable("startdate") String startDate,
                                                       @Parameter(description = "End Date", required = true)
                                                           @PathVariable("enddate") String endDate) throws StockPriceNotFoundException {
        try {
            StockPriceDTO stockPriceDTO = new StockPriceDTO();
            LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
            LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
            List<StockPrice> stockPrices = stockPriceService.getAllStockPricesByCompanyCodeBetweenDates(companyCode, start, end);
            stockPriceDTO.setStockPrices(stockPrices.stream().sorted(Comparator.comparing(StockPrice::getStockCreatedDate, Comparator.reverseOrder())).collect(Collectors.toList()));
            stockPriceDTO.setMinStockPrice(stockPrices.stream().min(Comparator.comparing(StockPrice::getStockPrice)).get().getStockPrice());
            stockPriceDTO.setMaxStockPrice(stockPrices.stream().max(Comparator.comparing(StockPrice::getStockPrice)).get().getStockPrice());
            stockPriceDTO.setAvgStockPrice(stockPrices.stream().mapToLong(StockPrice::getStockPrice).sum() / stockPrices.stream().count());
            return stockPriceDTO;
        } catch (StockPriceNotFoundException e) {
            log.error(e.getClass().getName() + " -- " + e.getMessage());
            throw new StockPriceNotFoundException(e.getMessage());
        }
    }

    @ApiIgnore
    @GetMapping(value = "/get/{companycode}")
    public double getLatestStockByCompanyCode(@PathVariable("companycode") Long companyCode) throws StockPriceNotFoundException {
        try {
            List<StockPrice> stockPrices = stockPriceService.getAllStockPricesByCompanyCode(companyCode);
            if (stockPrices.isEmpty()) return 0.0;
            return stockPrices.stream().sorted(Comparator.comparing(StockPrice::getStockCreatedDate, Comparator.reverseOrder())).findFirst().get().getStockPrice();
        } catch (StockPriceNotFoundException e) {
            log.error(e.getClass().getName() + " -- " + e.getMessage());
            throw new StockPriceNotFoundException(e.getMessage());
        }
    }

    @ApiIgnore
    @DeleteMapping(value = "/delete/{companycode}")
    public boolean deleteStockPricesByCompanyCode(@PathVariable("companycode") Long companyCode) {
        return stockPriceService.deleteAll(companyCode);
    }
}
