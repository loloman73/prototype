ALTER TABLE age_ranges
    ALTER COLUMN age_range TYPE VARCHAR(20) USING (age_range::VARCHAR(20));