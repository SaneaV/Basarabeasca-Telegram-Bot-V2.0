CREATE TABLE phone_numbers (
                phone_id BIGSERIAL NOT NULL,
                description VARCHAR(255),
                phone_number VARCHAR(255),
                PRIMARY KEY (phone_id)
);

ALTER TABLE phone_numbers ADD CONSTRAINT UK_PHONE_NUMBER_N UNIQUE (phone_number);