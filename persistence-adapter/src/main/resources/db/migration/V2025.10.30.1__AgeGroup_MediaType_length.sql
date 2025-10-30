ALTER TABLE age_groups
    ALTER COLUMN age_group TYPE VARCHAR(35) USING (age_group::VARCHAR(35));

ALTER TABLE media_types
    ALTER COLUMN media_type TYPE VARCHAR(35) USING (media_type::VARCHAR(35));