USE companyDb;

CREATE TABLE IF NOT EXISTS company (
    company_code        NUMERIC NOT NULL AUTO_INCREMENT PRIMARY KEY,
    company_name        VARCHAR(30) NOT NULL,
    company_ceo         VARCHAR(30) NOT NULL,
    company_turnover    UNSIGNED BIGINT(20) NOT NULL,
    company_website     VARCHAR(30) NOT NULL,
    company_enlisted_in VARCHAR(30) NOT NULL,
    latest_stock_price  DOUBLE,
    INDEX(company_code)
)engine=InnoDB;