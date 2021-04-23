package com.iiht.estock.stockprices.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString
@Entity
@Table(name = "stock_price")
@Schema(name = "StockPrice")
public class StockPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(example = "1", name = "stockId", description = "id of the stock price", type = "Long")
    private Long stockId;

    @Digits(integer = 10, fraction = 2)
    @Schema(example = "00.00", name = "stockPrice", description = "stock price", type = "Long")
    private Long stockPrice;

    @Schema(example = "2021-04-22", name = "stockCreatedDate",
            description = "creation date of the stock price",
            format = "string",
            pattern = "^\\d{4}-\\d{2}-\\d{2}$")
    private LocalDate stockCreatedDate;

    @Schema(example = "11111", name = "companyCode", description = "company code of the company", type = "Long")
    private Long companyCode;
}
