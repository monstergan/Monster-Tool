package com.monster.core.tool.node;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Create by monster gan on 2023/3/13 11:15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ForestNode extends BaseNode<ForestNode> {

    /**
     * 节点内容
     */
    private Object content;

    public ForestNode(Long id, Long parentId, Object content) {
        this.id = id;
        this.parentId = parentId;
        this.content = content;
    }
}
