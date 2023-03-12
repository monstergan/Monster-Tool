package com.monster.message;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.monster.dto.Link;


/**
 * Create by monster gan on 2023/3/12 21:23
 */
public class LinkMessage extends DingRobotMessage {

    @JsonProperty("msgtype")
    private String msgType = "link";

    public String getMsgType() {
        return msgType;
    }
    private Link link;

    public LinkMessage() {
    }

    public LinkMessage(Link link) {
        this.link = link;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }
}
