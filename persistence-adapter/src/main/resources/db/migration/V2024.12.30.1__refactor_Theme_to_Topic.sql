ALTER TABLE "ThemeTags" RENAME COLUMN "ThemeTagID" TO "TopicTagID";
ALTER TABLE "ThemeTags" RENAME CONSTRAINT "ThemeTags_pkey" TO "TopicTags_pkey";
ALTER TABLE "ThemeTags" RENAME TO "TopicTags";
ALTER TABLE "MediaGuide_ThemeTag" RENAME COLUMN "ThemeTagID" TO "TopicTagID";
ALTER TABLE "MediaGuide_ThemeTag" RENAME CONSTRAINT "MediaGuide_ThemeTag_pkey" TO "MediaGuide_TopicTag_pkey";
ALTER TABLE "MediaGuide_ThemeTag" RENAME CONSTRAINT "MediaGuide_ThemeTag_ThemeTagID_fkey" TO "MediaGuide_TopicTag_TopicTagID_fkey";
ALTER TABLE "MediaGuide_ThemeTag" RENAME CONSTRAINT "MediaGuide_ThemeTag_MediaGuideID_fkey" TO "MediaGuide_TopicTag_MediaGuideID_fkey";
ALTER TABLE "MediaGuide_ThemeTag" RENAME TO "MediaGuide_TopicTag";







