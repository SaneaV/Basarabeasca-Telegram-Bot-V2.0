CREATE TABLE fuel_price (
                id BIGSERIAL NOT NULL,
                type VARCHAR(20) NOT NULL,
                price decimal(7,2) NOT NULL,
                PRIMARY KEY (id)
);

ALTER TABLE fuel_price ADD CONSTRAINT UK_TYPE UNIQUE (type);