package com.monster.core.tool.node;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Create by monster gan on 2023/3/13 11:16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TreeNode extends BaseNode<TreeNode> {
    private String title;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long key;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long value;
}
