ALTER TABLE duration_types
    ADD active BOOLEAN;

ALTER TABLE duration_types
    ALTER COLUMN active SET NOT NULL;