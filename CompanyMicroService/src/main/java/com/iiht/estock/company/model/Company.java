package com.iiht.estock.company.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company")
@Schema(name = "Company")
public class Company {

    @Id
    @Schema(example = "11111", name = "companyCode", description = "company code of the company", type = "Long")
    private Long companyCode;

    @NotEmpty(message = "Company name is mandatory")
    @Schema(example = "XYZ Corp", name = "companyName", description = "name of the company", type = "String")
    private String companyName;

    @NotEmpty(message = "Company CEO is mandatory")
    @Schema(example = "Mr. ABC", name = "companyCEO", description = "name of CEO of the company", type = "String")
    private String companyCEO;

    @Min(value = 100000001L, message = "Company turn over must be greater than 10Cr")
    @Schema(example = "100000001", name = "companyTurnOver", description = "turn over of the company", type = "BigDecimal")
    private BigDecimal companyTurnOver;

    @NotEmpty(message = "Company website is mandatory")
    @Schema(example = "www.xyz.com", name = "companyWebsite", description = "website of the company", type = "String")
    private String companyWebsite;

    @NotEmpty(message = "Stock Exchange in which company is enlisted in is mandatory")
    @Schema(example = "NSE", name = "stockExchange", description = "stock exchange of the company", type = "String")
    private String stockExchange;

    @Schema(example = "400.00", name = "latestStockPrice", description = "latest stock price of the company", type = "Double")
    private double latestStockPrice = 0.00;
}
