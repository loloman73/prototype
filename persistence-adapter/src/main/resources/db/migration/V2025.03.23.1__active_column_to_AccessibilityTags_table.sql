ALTER TABLE accessibility_tags
    ADD active BOOLEAN;

ALTER TABLE accessibility_tags
    ALTER COLUMN active SET NOT NULL;