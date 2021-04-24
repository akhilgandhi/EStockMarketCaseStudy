DROP TABLE company IF EXISTS;

CREATE TABLE company (
    company_code        NUMERIC PRIMARY KEY,
    company_name        VARCHAR(30) NOT NULL,
    company_ceo         VARCHAR(30) NOT NULL,
    company_turnover    DOUBLE NOT NULL,
    company_website     VARCHAR(30) NOT NULL,
    company_enlisted_in VARCHAR(30) NOT NULL,
    latest_stock_price  DOUBLE
);
CREATE INDEX company_code_idx ON company (company_code);
