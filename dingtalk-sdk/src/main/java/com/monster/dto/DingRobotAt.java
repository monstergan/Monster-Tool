package com.monster.dto;

import java.util.List;

/**
 * Create by monster gan on 2023/3/12 21:21
 */
public class DingRobotAt {

    private List<String> atMobiles;

    private List<String> atUserIds;

    private Boolean isAtAll = Boolean.FALSE;


    public List<String> getAtMobiles() {
        return atMobiles;
    }

    public void setAtMobiles(List<String> atMobiles) {
        this.atMobiles = atMobiles;
    }

    public List<String> getAtUserIds() {
        return atUserIds;
    }

    public void setAtUserIds(List<String> atUserIds) {
        this.atUserIds = atUserIds;
    }

    public Boolean getAtAll() {
        return isAtAll;
    }

    public void setAtAll(Boolean atAll) {
        isAtAll = atAll;
    }

    public String getAtMobilesString() {
        if (atMobiles == null) return "";
        StringBuilder at = new StringBuilder();
        for (String mobile : atMobiles) {
            at.append("@").append(mobile);
        }
        return at.toString();
    }
}
