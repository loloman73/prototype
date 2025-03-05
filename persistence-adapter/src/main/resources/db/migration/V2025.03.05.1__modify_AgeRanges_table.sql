ALTER TABLE age_ranges
    ADD min_age SMALLINT;

ALTER TABLE age_ranges
    ADD max_age SMALLINT;

ALTER TABLE age_ranges
    ALTER COLUMN max_age SET NOT NULL;

ALTER TABLE age_ranges
    ALTER COLUMN min_age SET NOT NULL;

ALTER TABLE age_ranges
    ALTER COLUMN age_range SET NOT NULL;