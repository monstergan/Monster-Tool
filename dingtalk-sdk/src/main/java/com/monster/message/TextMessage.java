package com.monster.message;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.monster.dto.Text;


/**
 * Create by monster gan on 2023/3/12 21:24
 */
public class TextMessage extends DingRobotMessage {
    @JsonProperty("msgtype")
    private String msgType = "text";

    public String getMsgType() {
        return msgType;
    }

    private Text text;

    public TextMessage() {
    }

    public TextMessage(Text text) {
        this.text = text;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }
}
