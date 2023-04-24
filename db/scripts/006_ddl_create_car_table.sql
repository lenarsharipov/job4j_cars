CREATE TABLE car (
    id          SERIAL  PRIMARY KEY,
    engine_id   INT     NOT NULL REFERENCES engine(id)
);