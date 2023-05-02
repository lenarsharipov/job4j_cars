CREATE TABLE history_owners (
    id          SERIAL  PRIMARY KEY,
    owner_id    INT     NOT NULL REFERENCES owners(id),
    car_id      INT     NOT NULL REFERENCES car(id)
);