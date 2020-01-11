package com.cycloneboy.springcloud.slmall.module.portal.entity;


import com.cycloneboy.springcloud.slmall.common.base.BaseXCloudEntity;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

/**
 * @author Exrick
 */
@Data
@Entity
@Table(name = "t_role_permission")
public class RolePermission extends BaseXCloudEntity {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "角色id")
  private String roleId;

  @ApiModelProperty(value = "权限id")
  private String permissionId;
}