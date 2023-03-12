package com.monster.message;


import com.monster.dto.DingRobotAt;

/**
 * Create by monster gan on 2023/3/12 21:23
 */
public abstract class DingRobotMessage {

    private DingRobotAt at;

    public DingRobotAt getAt() {
        return at;
    }

    public void setAt(DingRobotAt at) {
        this.at = at;
    }
}
