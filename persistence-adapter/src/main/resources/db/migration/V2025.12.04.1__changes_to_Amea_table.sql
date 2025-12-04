ALTER TABLE media_guides
    DROP CONSTRAINT media_guides_amea_friendly_type_id_fkey;

CREATE TABLE amea_tags
(
    amea_tag_id UUID         NOT NULL,
    amea_tag    VARCHAR(35)  NOT NULL,
    description VARCHAR(250) NOT NULL,
    active      BOOLEAN      NOT NULL,
    CONSTRAINT pk_amea_tags PRIMARY KEY (amea_tag_id)
);

ALTER TABLE media_guides
    ADD amea_tag_id UUID;

ALTER TABLE media_guides
    ADD CONSTRAINT FK_MEDIA_GUIDES_ON_AMEA_TAG FOREIGN KEY (amea_tag_id) REFERENCES amea_tags (amea_tag_id);

DROP TABLE amea_friendly_types CASCADE;

ALTER TABLE media_guides
    DROP COLUMN amea_friendly_type_id;