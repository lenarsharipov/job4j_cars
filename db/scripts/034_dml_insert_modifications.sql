INSERT INTO modification (make_id, model_id, generation_id, start_year_id, end_year_id,
                          trim_id, body_style_id, engine_id,
                          door_id, transmission_id, drivetrain_id)
-- [Renault], [Duster], [1st Generation], [2010], [2015],
-- [Privilege], [SUV / Crossover], [Diesel, 86hp, 1.5, 4cyl],
-- [5 door], [Manual], [Front-wheel drive]
--
VALUES  (3, 1, 1, 14, 19,
         7, 5, 6,
         4, 4, 2),

-- [Renault], [Duster], [1st Generation Facelift], [2015], [2021],
-- [Expression], [SUV / Crossover], [Gasoline, 143hp, 2.0, 4cyl],
-- [5 door], [Automatic], [All-wheel drive]
        (3, 1, 2, 19, 25,
         6, 5, 5,
         4, 2, 1),

-- [Renault], [Duster], [2nd Generation], [2020], [null = Present],
-- [Access], [SUV / Crossover], [Gasoline, 114hp, 1.6, 4cyl],
-- [5 door], [Manual], [Front-wheel drive]
        (3, 1, 3, 24, null,
         1, 5, 1,
         4, 4, 2);