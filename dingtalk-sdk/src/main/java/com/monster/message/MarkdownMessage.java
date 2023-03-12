package com.monster.message;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.monster.dto.Markdown;


/**
 * Create by monster gan on 2023/3/12 21:23
 */
public class MarkdownMessage extends DingRobotMessage {

    @JsonProperty("msgtype")
    private String msgType = "markdown";

    public String getMsgType() {
        return msgType;
    }

    private Markdown markdown;

    public MarkdownMessage() {
    }

    public MarkdownMessage(Markdown markdown) {
        this.markdown = markdown;
    }

    public Markdown getMarkdown() {
        return markdown;
    }

    public void setMarkdown(Markdown markdown) {
        this.markdown = markdown;
    }
}
