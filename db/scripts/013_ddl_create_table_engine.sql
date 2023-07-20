CREATE TABLE engine (
    id                  SERIAL  PRIMARY KEY,
    fuel_type_id        INT     NOT NULL,
    horsepower_id       INT     NOT NULL,
    engine_capacity_id  INT     NOT NULL,
    cylinder_id         INT     NOT NULL
);