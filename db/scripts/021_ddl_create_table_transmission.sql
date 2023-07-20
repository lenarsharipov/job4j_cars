CREATE TABLE transmission (
    id              SERIAL  PRIMARY KEY,
    type            VARCHAR NOT NULL UNIQUE
);