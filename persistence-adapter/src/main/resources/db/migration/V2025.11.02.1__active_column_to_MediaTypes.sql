ALTER TABLE media_types
    ADD active BOOLEAN;

ALTER TABLE media_types
    ALTER COLUMN active SET NOT NULL;