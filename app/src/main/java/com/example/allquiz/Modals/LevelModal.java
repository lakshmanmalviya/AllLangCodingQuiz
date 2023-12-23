package com.example.allquiz.Modals;

public class LevelModal {
    String levelName,levelId;

    public LevelModal() {
    }

    public LevelModal(String levelName, String levelId) {
        this.levelName = levelName;
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }
}
