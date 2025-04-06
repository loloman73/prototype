ALTER TABLE age_groups
    ADD active BOOLEAN;

ALTER TABLE age_groups
    ALTER COLUMN active SET NOT NULL;