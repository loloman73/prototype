ALTER TABLE languages
    ADD active BOOLEAN;

ALTER TABLE languages
    ADD code VARCHAR(3);

ALTER TABLE languages
    ADD english_name VARCHAR(35);

ALTER TABLE languages
    ADD native_name VARCHAR(35);

ALTER TABLE languages
    ALTER COLUMN active SET NOT NULL;

ALTER TABLE languages
    ALTER COLUMN code SET NOT NULL;

ALTER TABLE languages
    ALTER COLUMN english_name SET NOT NULL;

ALTER TABLE languages
    ALTER COLUMN native_name SET NOT NULL;

ALTER TABLE languages
    DROP COLUMN language;