CREATE TABLE model (
    id          SERIAL  PRIMARY KEY,
    name        VARCHAR NOT NULL UNIQUE
);

CREATE TABLE make_model (
    id              SERIAL  PRIMARY KEY,
    make_id         INT     NOT NULL,
    model_id        INT     NOT NULL
);

CREATE TABLE model_body_style (
    id              SERIAL  PRIMARY KEY,
    model_id        INT     NOT NULL REFERENCES model(id),
    body_style_id   INT     NOT NULL REFERENCES body_style(id)
);