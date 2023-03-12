package com.monster.message;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.monster.dto.FeedCard;

/**
 * Create by monster gan on 2023/3/12 21:23
 */
public class FeedCardMessage extends DingRobotMessage {


    @JsonProperty("msgtype")
    private String msgType = "feedCard";

    public String getMsgType() {
        return msgType;
    }

    private FeedCard feedCard;

    public FeedCardMessage() {
    }

    public FeedCardMessage(FeedCard feedCard) {
        this.feedCard = feedCard;
    }
    public FeedCard getFeedCard() {
        return feedCard;
    }

    public void setFeedCard(FeedCard feedCard) {
        this.feedCard = feedCard;
    }
}
