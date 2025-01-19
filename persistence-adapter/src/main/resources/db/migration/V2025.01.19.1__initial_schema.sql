CREATE TABLE accessibility_tags
(
    accessibility_tag_id UUID NOT NULL,
    accessibility_tag    VARCHAR,
    description          VARCHAR,
    CONSTRAINT accessibility_tags_pkey PRIMARY KEY (accessibility_tag_id)
);

CREATE TABLE admins
(
    admin_id UUID NOT NULL,
    name     VARCHAR,
    CONSTRAINT admins_pkey PRIMARY KEY (admin_id)
);

CREATE TABLE age_ranges
(
    age_range_id UUID NOT NULL,
    age_range    VARCHAR,
    CONSTRAINT age_ranges_pkey PRIMARY KEY (age_range_id)
);

CREATE TABLE amea_friendly_types
(
    amea_friendly_type_id UUID NOT NULL,
    amea_friendly_type    VARCHAR,
    CONSTRAINT amea_friendly_types_pkey PRIMARY KEY (amea_friendly_type_id)
);

CREATE TABLE duration_types
(
    duration_type_id UUID NOT NULL,
    duration_type    VARCHAR,
    CONSTRAINT duration_types_pkey PRIMARY KEY (duration_type_id)
);

CREATE TABLE languages
(
    language_id UUID NOT NULL,
    language    VARCHAR,
    CONSTRAINT languages_pkey PRIMARY KEY (language_id)
);

CREATE TABLE media_guide_age_range
(
    media_guide_id UUID NOT NULL,
    age_range_id   UUID NOT NULL,
    CONSTRAINT media_guide_age_range_pkey PRIMARY KEY (media_guide_id, age_range_id)
);

CREATE TABLE media_guide_topic_tag
(
    media_guide_id UUID NOT NULL,
    topic_tag_id   UUID NOT NULL
);

CREATE TABLE media_guides
(
    media_guide_id        UUID NOT NULL,
    tour_point_id         UUID,
    language_id           UUID,
    media_file_name       VARCHAR,
    media_type_id         UUID,
    duration              SMALLINT,
    duration_type_id      UUID,
    amea_friendly_type_id UUID,
    CONSTRAINT media_guides_pkey PRIMARY KEY (media_guide_id)
);

CREATE TABLE media_types
(
    media_type_id UUID NOT NULL,
    media_type    VARCHAR,
    CONSTRAINT media_types_pkey PRIMARY KEY (media_type_id)
);

CREATE TABLE topic_tags
(
    topic_tag_id UUID NOT NULL,
    topic_tag    VARCHAR,
    description  VARCHAR,
    CONSTRAINT topic_tags_pkey PRIMARY KEY (topic_tag_id)
);

CREATE TABLE tour_point_accessibility_tag
(
    tour_point_id        UUID NOT NULL,
    accessibility_tag_id UUID NOT NULL,
    CONSTRAINT tour_point_accessibility_tag_pkey PRIMARY KEY (tour_point_id, accessibility_tag_id)
);

CREATE TABLE tour_points
(
    tour_point_id UUID NOT NULL,
    tour_id       UUID,
    title         VARCHAR,
    description   VARCHAR,
    coordinates   POINT,
    CONSTRAINT tour_points_pkey PRIMARY KEY (tour_point_id)
);

CREATE TABLE tours
(
    tour_id         UUID NOT NULL,
    xenagos_id      UUID,
    title           VARCHAR,
    description     VARCHAR,
    price           DECIMAL,
    photo_file_name UUID,
    rate_stars      SMALLINT,
    rate_reviews    INTEGER,
    coordinates_avg POINT,
    CONSTRAINT tours_pkey PRIMARY KEY (tour_id)
);

CREATE TABLE xenagoi
(
    xenagos_id      UUID NOT NULL,
    name            VARCHAR,
    photo_file_name UUID,
    CONSTRAINT xenagoi_pkey PRIMARY KEY (xenagos_id)
);

ALTER TABLE media_guide_topic_tag
    ADD CONSTRAINT media_guide_topic_tag_pkey PRIMARY KEY (topic_tag_id, media_guide_id);

ALTER TABLE media_guide_age_range
    ADD CONSTRAINT media_guide_age_range_age_range_id_fkey FOREIGN KEY (age_range_id) REFERENCES age_ranges (age_range_id) ON DELETE NO ACTION;

ALTER TABLE media_guide_age_range
    ADD CONSTRAINT media_guide_age_range_media_guide_id_fkey FOREIGN KEY (media_guide_id) REFERENCES media_guides (media_guide_id) ON DELETE NO ACTION;

ALTER TABLE media_guide_topic_tag
    ADD CONSTRAINT media_guide_topic_tag_media_guide_id_fkey FOREIGN KEY (media_guide_id) REFERENCES media_guides (media_guide_id) ON DELETE NO ACTION;

ALTER TABLE media_guide_topic_tag
    ADD CONSTRAINT media_guide_topic_tag_topic_tag_id_fkey FOREIGN KEY (topic_tag_id) REFERENCES topic_tags (topic_tag_id) ON DELETE NO ACTION;

ALTER TABLE media_guides
    ADD CONSTRAINT media_guides_amea_friendly_type_id_fkey FOREIGN KEY (amea_friendly_type_id) REFERENCES amea_friendly_types (amea_friendly_type_id) ON DELETE NO ACTION;

ALTER TABLE media_guides
    ADD CONSTRAINT media_guides_duration_type_id_fkey FOREIGN KEY (duration_type_id) REFERENCES duration_types (duration_type_id) ON DELETE NO ACTION;

ALTER TABLE media_guides
    ADD CONSTRAINT media_guides_language_id_fkey FOREIGN KEY (language_id) REFERENCES languages (language_id) ON DELETE NO ACTION;

ALTER TABLE media_guides
    ADD CONSTRAINT media_guides_media_type_id_fkey FOREIGN KEY (media_type_id) REFERENCES media_types (media_type_id) ON DELETE NO ACTION;

ALTER TABLE media_guides
    ADD CONSTRAINT media_guides_tour_point_id_fkey FOREIGN KEY (tour_point_id) REFERENCES tour_points (tour_point_id) ON DELETE NO ACTION;

ALTER TABLE tour_point_accessibility_tag
    ADD CONSTRAINT tour_point_accessibility_tag_accessibility_tag_id_fkey FOREIGN KEY (accessibility_tag_id) REFERENCES accessibility_tags (accessibility_tag_id) ON DELETE NO ACTION;

ALTER TABLE tour_point_accessibility_tag
    ADD CONSTRAINT tour_point_accessibility_tag_tour_point_id_fkey FOREIGN KEY (tour_point_id) REFERENCES tour_points (tour_point_id) ON DELETE NO ACTION;

ALTER TABLE tour_points
    ADD CONSTRAINT tour_points_tour_id_fkey FOREIGN KEY (tour_id) REFERENCES tours (tour_id) ON DELETE NO ACTION;

ALTER TABLE tours
    ADD CONSTRAINT tours_xenagos_id_fkey FOREIGN KEY (xenagos_id) REFERENCES xenagoi (xenagos_id) ON DELETE NO ACTION;