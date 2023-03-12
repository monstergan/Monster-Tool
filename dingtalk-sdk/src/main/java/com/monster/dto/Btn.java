package com.monster.dto;

/**
 * Create by monster gan on 2023/3/12 21:21
 */
public class Btn {

    private String title;

    private String actionURL;


    public Btn() {
    }

    public Btn(String title, String actionURL) {
        this.title = title;
        this.actionURL = actionURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActionURL() {
        return actionURL;
    }

    public void setActionURL(String actionURL) {
        this.actionURL = actionURL;
    }
}
