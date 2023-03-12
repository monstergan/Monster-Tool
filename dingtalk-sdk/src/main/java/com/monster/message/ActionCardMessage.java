package com.monster.message;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.monster.dto.ActionCard;


/**
 * Create by monster gan on 2023/3/12 21:23
 */
public class ActionCardMessage extends DingRobotMessage {

    @JsonProperty("msgtype")
    private String msgType = "actionCard";

    public String getMsgType() {
        return msgType;
    }

    private ActionCard actionCard;

    public ActionCardMessage() {
    }

    public ActionCardMessage(ActionCard actionCard) {
        this.actionCard = actionCard;
    }

    public ActionCard getActionCard() {
        return actionCard;
    }

    public void setActionCard(ActionCard actionCard) {
        this.actionCard = actionCard;
    }
}
