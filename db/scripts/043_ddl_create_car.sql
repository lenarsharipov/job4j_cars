CREATE TABLE car (
    id                  SERIAL  PRIMARY KEY,
    name                VARCHAR NOT NULL,
    mileage             INT     NOT NULL,
    owner_name          VARCHAR NOT NULL,
    year_id             INT     NOT NULL REFERENCES years(id),
    condition_id        INT     NOT NULL REFERENCES condition(id),
    color_id            INT     NOT NULL REFERENCES color(id),
    owner_count_id      INT     NOT NULL REFERENCES owner_count(id),
    modification_id     INT     NOT NULL REFERENCES modification(id)
);