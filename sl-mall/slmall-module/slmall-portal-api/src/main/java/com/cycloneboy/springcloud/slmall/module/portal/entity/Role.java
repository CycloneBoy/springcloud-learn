package com.cycloneboy.springcloud.slmall.module.portal.entity;

import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudEntity;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;

/**
 * @author Exrickx
 */
@Data
@Entity
@Table(name = "t_role")
public class Role extends BaseXCloudEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色名 以ROLE_开头")
    private String name;

    @ApiModelProperty(value = "是否为注册默认角色")
    private Boolean defaultRole;

    @ApiModelProperty(value = "备注")
    private String description;

    @Transient
    @ApiModelProperty(value = "拥有权限")
    private List<Permission> permissions;
}
