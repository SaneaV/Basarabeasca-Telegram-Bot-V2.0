CREATE TABLE exchange_rates (
                exchange_id BIGSERIAL NOT NULL,
                currency VARCHAR(20) NOT NULL,
                value VARCHAR(20) NOT NULL,
                PRIMARY KEY (exchange_id)
);

ALTER TABLE exchange_rates ADD CONSTRAINT UK_CURRENCY UNIQUE (currency);