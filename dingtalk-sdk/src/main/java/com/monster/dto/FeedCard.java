package com.monster.dto;

import java.util.List;

/**
 * Create by monster gan on 2023/3/12 21:22
 */
public class FeedCard {

    private List<Link> links;

    public FeedCard() {
    }

    public FeedCard(List<Link> links) {
        this.links = links;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
