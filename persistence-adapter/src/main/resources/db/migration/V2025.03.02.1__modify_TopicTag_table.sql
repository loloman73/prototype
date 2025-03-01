ALTER TABLE topic_tags
    ADD active BOOLEAN;

ALTER TABLE topic_tags
    ALTER COLUMN active SET NOT NULL;

ALTER TABLE topic_tags
    ALTER COLUMN description TYPE VARCHAR(250) USING (description::VARCHAR(250));

ALTER TABLE topic_tags
    ALTER COLUMN description SET NOT NULL;

ALTER TABLE topic_tags
    ALTER COLUMN topic_tag TYPE VARCHAR(35) USING (topic_tag::VARCHAR(35));

ALTER TABLE topic_tags
    ALTER COLUMN topic_tag SET NOT NULL;