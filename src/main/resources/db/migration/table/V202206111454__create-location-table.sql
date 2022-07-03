CREATE TABLE location (
                id BIGSERIAL NOT NULL,
                name VARCHAR(100) NOT NULL,
                latitude VARCHAR(20) NOT NULL,
                longitude VARCHAR(20) NOT NULL,
                PRIMARY KEY (id)
);

ALTER TABLE location ADD CONSTRAINT UK_LOCATION UNIQUE (latitude, longitude);