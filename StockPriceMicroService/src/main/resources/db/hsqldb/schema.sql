DROP TABLE stock_price IF EXISTS;

CREATE TABLE stock_price (
    id              NUMERIC IDENTITY PRIMARY KEY,
    stock_prices    VARCHAR(20) NOT NULL,
    stock_date_time DATE NOT NULL,
    company_code    NUMERIC NOT NULL
);
CREATE INDEX stock_price_company_code_idx ON stock_price (company_code);
CREATE INDEX stock_price_stock_date_time_idx ON stock_price (stock_date_time);
