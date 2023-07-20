CREATE TABLE modification (
    id                          SERIAL  PRIMARY KEY,
    make_id                     INT     NOT NULL REFERENCES make(id),
    model_id                    INT     NOT NULL REFERENCES model(id),
    generation_id               INT     NOT NULL REFERENCES generation(id),
    start_year_id               INT     NOT NULL REFERENCES years(id),
    end_year_id                 INT     REFERENCES years(id),
    trim_id                     INT     NOT NULL REFERENCES trim(id),
    body_style_id               INT     NOT NULL REFERENCES body_style(id),
    engine_id                   INT     NOT NULL REFERENCES engine(id),
    door_id                     INT     NOT NULL REFERENCES door(id),
    transmission_id             INT     NOT NULL REFERENCES transmission(id),
    drivetrain_id               INT     NOT NULL REFERENCES drivetrain(id)
);