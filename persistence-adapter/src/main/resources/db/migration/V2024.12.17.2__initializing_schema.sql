CREATE TABLE "Xenagoi" (
                           "XenagosID" integer PRIMARY KEY,
                           "Name" varchar,
                           "PhotoURL" varchar
);

CREATE TABLE "Tours" (
                         "TourID" integer PRIMARY KEY,
                         "XenagosID" integer,
                         "Title" varchar,
                         "Description" varchar,
                         "Price" money,
                         "PhotoURL" varchar,
                         "RateStars" smallint,
                         "RateReviews" integer,
                         "CoordinatesAvg" point
);

CREATE TABLE "TourPoints" (
                              "TourPointID" integer PRIMARY KEY,
                              "TourID" integer,
                              "Title" varchar,
                              "Description" varchar,
                              "Coordinates" point
);

CREATE TABLE "MediaGuides" (
                               "MediaGuideID" bigint PRIMARY KEY,
                               "TourPointID" integer,
                               "LanguageID" smallint,
                               "MediaFileURL" varchar,
                               "MediaTypeID" smallint,
                               "Duration" smallint,
                               "DurationTypeID" smallint,
                               "AmeaFriendlyTypeID" smallint
);

CREATE TABLE "AccessibilityTags" (
                                     "AccessibilityTagID" smallint PRIMARY KEY,
                                     "Name" varchar,
                                     "Description" varchar
);

CREATE TABLE "TourPoint_AccessibilityTag" (
                                              "TourPointID" integer,
                                              "AccessibilityTagID" smallint,
                                              PRIMARY KEY ("TourPointID", "AccessibilityTagID")
);

CREATE TABLE "ThemeTags" (
                             "ThemeTagID" smallint PRIMARY KEY,
                             "Name" varchar,
                             "Description" varchar
);

CREATE TABLE "MediaGuide_ThemeTag" (
                                       "MediaGuideID" bigint,
                                       "ThemeTagID" smallint,
                                       PRIMARY KEY ("ThemeTagID", "MediaGuideID")
);

CREATE TABLE "AgeRanges" (
                             "AgeRangeID" smallint PRIMARY KEY,
                             "AgeRange" varchar
);

CREATE TABLE "MediaGuide_AgeRange" (
                                       "MediaGuideID" bigint,
                                       "AgeRangeID" smallint,
                                       PRIMARY KEY ("MediaGuideID", "AgeRangeID")
);

CREATE TABLE "Languages" (
                             "LanguageID" smallint UNIQUE PRIMARY KEY,
                             "Name" varchar
);

CREATE TABLE "MediaTypes" (
                              "MediaTypeID" smallint PRIMARY KEY,
                              "Name" varchar
);

CREATE TABLE "DurationTypes" (
                                 "DurationTypeID" smallint PRIMARY KEY,
                                 "Name" varchar
);

CREATE TABLE "AmeaFriendlyTypes" (
                                     "AmeaFriendlyTypeID" smallint PRIMARY KEY,
                                     "Name" varchar
);

ALTER TABLE "Tours" ADD FOREIGN KEY ("XenagosID") REFERENCES "Xenagoi" ("XenagosID");

ALTER TABLE "TourPoints" ADD FOREIGN KEY ("TourID") REFERENCES "Tours" ("TourID");

ALTER TABLE "MediaGuides" ADD FOREIGN KEY ("TourPointID") REFERENCES "TourPoints" ("TourPointID");

ALTER TABLE "TourPoint_AccessibilityTag" ADD FOREIGN KEY ("TourPointID") REFERENCES "TourPoints" ("TourPointID");

ALTER TABLE "MediaGuide_ThemeTag" ADD FOREIGN KEY ("MediaGuideID") REFERENCES "MediaGuides" ("MediaGuideID");

ALTER TABLE "MediaGuide_AgeRange" ADD FOREIGN KEY ("MediaGuideID") REFERENCES "MediaGuides" ("MediaGuideID");

ALTER TABLE "MediaGuides" ADD FOREIGN KEY ("LanguageID") REFERENCES "Languages" ("LanguageID");

ALTER TABLE "MediaGuides" ADD FOREIGN KEY ("MediaTypeID") REFERENCES "MediaTypes" ("MediaTypeID");

ALTER TABLE "MediaGuides" ADD FOREIGN KEY ("DurationTypeID") REFERENCES "DurationTypes" ("DurationTypeID");

ALTER TABLE "MediaGuides" ADD FOREIGN KEY ("AmeaFriendlyTypeID") REFERENCES "AmeaFriendlyTypes" ("AmeaFriendlyTypeID");

ALTER TABLE "TourPoint_AccessibilityTag" ADD FOREIGN KEY ("AccessibilityTagID") REFERENCES "AccessibilityTags" ("AccessibilityTagID");

ALTER TABLE "MediaGuide_ThemeTag" ADD FOREIGN KEY ("ThemeTagID") REFERENCES "ThemeTags" ("ThemeTagID");

ALTER TABLE "MediaGuide_AgeRange" ADD FOREIGN KEY ("AgeRangeID") REFERENCES "AgeRanges" ("AgeRangeID");
