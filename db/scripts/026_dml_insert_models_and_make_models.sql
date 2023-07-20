INSERT INTO model(name)
VALUES  ('Duster'), ('Focus'), ('i40'), ('V90'), ('Logan'), ('Captur');

INSERT INTO make_model(make_id, model_id)
VALUES  (1, 2),     -- 1    [FORD], [Focus]
        (2, 3),     -- 2    [HYUNDAI], [I40]
        (3, 1),     -- 3    [RENAULT], [Duster]
        (3, 5),     -- 4    [RENAULT], [Logan]
        (3, 6),     -- 5    [RENAULT], [Captur]
        (4, 4);     -- 6    [VOLVO], [V90]

INSERT INTO model_body_style(model_id, body_style_id)
VALUES  (1, 5);     -- 1    [DUSTER], [SUV / CROSSOVER]