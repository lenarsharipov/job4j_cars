CREATE TABLE auto_user (
    id              SERIAL    PRIMARY KEY,
    login           VARCHAR   NOT NULL UNIQUE,
    password        VARCHAR   NOT NULL
);

CREATE TABLE post (
    id              SERIAL    PRIMARY KEY,
    description     VARCHAR   NOT NULL,
    created         TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
    is_sold         BOOL      DEFAULT FALSE NOT NULL,
    user_id         INT       NOT NULL REFERENCES auto_user(id),
    has_photo       BOOL      DEFAULT FALSE NOT NULL
);

CREATE TABLE file (
    id              SERIAL    PRIMARY KEY,
    name            VARCHAR   NOT NULL,
    path            VARCHAR   NOT NULL UNIQUE,
    post_id         INT
);
INSERT INTO file(name, path) VALUES('default_auto_poster.png', 'files/default/default_auto_poster.png');

CREATE TABLE years (
    id              SERIAL    PRIMARY KEY,
    name            INT       NOT NULL UNIQUE
);

INSERT INTO years(name)
VALUES  (1997),
        (1998),
        (1999),
        (2000),
        (2001),
        (2002),
        (2003),
        (2004),
        (2005),
        (2006),
        (2007),
        (2008),
        (2009),
        (2010),
        (2011),
        (2012),
        (2013),
        (2014),
        (2015),
        (2016),
        (2017),
        (2018),
        (2019),
        (2020),
        (2021),
        (2022),
        (2023);