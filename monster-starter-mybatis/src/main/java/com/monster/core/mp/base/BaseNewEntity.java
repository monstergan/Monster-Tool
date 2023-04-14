package com.monster.core.mp.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.monster.core.tool.utils.DateUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类
 */
@Data
public class BaseNewEntity implements Serializable {

	/**
	 * 主键id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "主键id")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	/**
	 * 创建人
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "创建人")
	private Long createUser;
	/**
	 * 创建人名称
	 */
	@ApiModelProperty(value = "创建人名称")
	private String createUserName;

	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	/**
	 * 更新人
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@ApiModelProperty(value = "更新人")
	private Long updateUser;
	/**
	 * 修改人名称
	 */
	@ApiModelProperty(value = "修改人名称")
	private String updateUserName;
	/**
	 * 更新时间
	 */
	@DateTimeFormat(pattern = DateUtil.PATTERN_DATETIME)
	@JsonFormat(pattern = DateUtil.PATTERN_DATETIME)
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;

}
