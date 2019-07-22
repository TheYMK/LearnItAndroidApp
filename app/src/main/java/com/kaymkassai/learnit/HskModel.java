package com.kaymkassai.learnit;

public class HskModel {
    String id;
    String character;
    String pinyin;
    String meaning;

    public HskModel(String id, String character, String pinyin, String meaning) {
        this.id = id;
        this.character = character;
        this.pinyin = pinyin;
        this.meaning = meaning;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }


}
