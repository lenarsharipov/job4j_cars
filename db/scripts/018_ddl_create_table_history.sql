CREATE TABLE history (
    id        SERIAL PRIMARY KEY,
    start_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_at    TIMESTAMP WITHOUT TIME ZONE NOT NULL
)