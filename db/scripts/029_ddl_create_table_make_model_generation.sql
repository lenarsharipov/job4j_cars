CREATE TABLE make_model_generation (
    id              SERIAL  PRIMARY KEY,
    make_model_id   INT     NOT NULL REFERENCES model(id),
    generation_id   INT     NOT NULL REFERENCES generation(id),
    start_year_id   INT     NOT NULL REFERENCES years(id),
    end_year_id     INT     REFERENCES years(id)
);