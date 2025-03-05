ALTER TABLE media_guide_age_range
    DROP CONSTRAINT media_guide_age_range_age_range_id_fkey;

ALTER TABLE media_guide_age_range
    DROP CONSTRAINT media_guide_age_range_media_guide_id_fkey;

CREATE TABLE age_groups
(
    age_group_id UUID        NOT NULL,
    age_group    VARCHAR(20) NOT NULL,
    min_age      SMALLINT    NOT NULL,
    max_age      SMALLINT    NOT NULL,
    CONSTRAINT pk_age_groups PRIMARY KEY (age_group_id)
);

CREATE TABLE media_guide_age_group
(
    age_group_id   UUID NOT NULL,
    media_guide_id UUID NOT NULL,
    CONSTRAINT pk_media_guide_age_group PRIMARY KEY (age_group_id, media_guide_id)
);

ALTER TABLE media_guide_age_group
    ADD CONSTRAINT fk_medguiagegro_on_age_group_jpa_entity FOREIGN KEY (age_group_id) REFERENCES age_groups (age_group_id);

ALTER TABLE media_guide_age_group
    ADD CONSTRAINT fk_medguiagegro_on_media_guide_jpa_entity FOREIGN KEY (media_guide_id) REFERENCES media_guides (media_guide_id);

DROP TABLE age_ranges CASCADE;

DROP TABLE media_guide_age_range CASCADE;