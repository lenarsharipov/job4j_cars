ALTER TABLE car ADD COLUMN owner_id INT REFERENCES owner(id) NOT NULL;