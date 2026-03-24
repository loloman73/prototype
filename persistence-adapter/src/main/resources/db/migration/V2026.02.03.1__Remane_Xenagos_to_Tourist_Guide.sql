ALTER TABLE tours
    DROP CONSTRAINT tours_xenagos_id_fkey;

CREATE TABLE tourist_guides
(
    tourist_guide_id UUID NOT NULL,
    name             TEXT,
    photo_file_name  UUID,
    CONSTRAINT pk_tourist_guides PRIMARY KEY (tourist_guide_id)
);

ALTER TABLE tours
    ADD tourist_guide_id UUID;

ALTER TABLE tours
    ADD CONSTRAINT FK_TOURS_ON_TOURIST_GUIDE FOREIGN KEY (tourist_guide_id) REFERENCES tourist_guides (tourist_guide_id);

DROP TABLE xenagoi CASCADE;

ALTER TABLE tours
    DROP COLUMN xenagos_id;