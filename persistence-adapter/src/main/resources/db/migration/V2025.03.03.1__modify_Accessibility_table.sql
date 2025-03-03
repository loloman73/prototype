ALTER TABLE accessibility_tags
    ALTER COLUMN accessibility_tag SET NOT NULL;

ALTER TABLE accessibility_tags
    ALTER COLUMN description SET NOT NULL;

ALTER TABLE accessibility_tags
    ALTER COLUMN description TYPE VARCHAR(250) USING (description::VARCHAR(250));

ALTER TABLE accessibility_tags
    ALTER COLUMN accessibility_tag TYPE VARCHAR(35) USING (accessibility_tag::VARCHAR(35));