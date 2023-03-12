package com.monster.dto;

/**
 * Create by monster gan on 2023/3/12 21:22
 */
public class Markdown {

    private String title;

    private String text;

    public Markdown() {
    }

    public Markdown(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
