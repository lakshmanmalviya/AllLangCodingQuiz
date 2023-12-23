package com.example.allquiz.Modals;

public class LangModal {
    String langName,langId;

    public LangModal() {
    }

    public LangModal(String langName, String langId) {
        this.langName = langName;
        this.langId = langId;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }
}
