CREATE TABLE auto_user (
    id          SERIAL PRIMARY KEY,
    login       VARCHAR NOT NULL UNIQUE,
    password    VARCHAR NOT NULL
);

CREATE TABLE post (
    id              SERIAL PRIMARY KEY,
    description     VARCHAR NOT NULL,
    created         TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    auto_user_id    INT REFERENCES auto_user (id) NOT NULL
);