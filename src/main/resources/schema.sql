-- CREATE WRITE MODEL

CREATE TABLE IF NOT EXISTS data
(
    id   SERIAL,
    data JSONB
);

-- CREATE READ MODEL
CREATE INDEX IF NOT EXISTS role ON data ((data ->> 'role'));

CREATE OR REPLACE VIEW author AS
SELECT id AS author_id, data ->> 'name' AS author_name
FROM data
WHERE data ->> 'role' = 'author';

CREATE OR REPLACE VIEW book AS
SELECT id AS author_id, jsonb_array_elements_text(data -> 'books') AS book_name
FROM data
WHERE data ->> 'role' = 'author';