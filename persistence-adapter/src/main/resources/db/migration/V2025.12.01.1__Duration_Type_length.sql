ALTER TABLE duration_types
    ALTER COLUMN duration_type TYPE VARCHAR(35) USING (duration_type::VARCHAR(35));

ALTER TABLE duration_types
    ALTER COLUMN duration_type SET NOT NULL;