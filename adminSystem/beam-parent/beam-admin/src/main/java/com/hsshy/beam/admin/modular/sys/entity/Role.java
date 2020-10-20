package com.hsshy.beam.admin.modular.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hsshy.beam.web.modular.base.entity.RestEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


/**
 * 角色
 *
 * @author hs
 */
@Setter
@Getter
@TableName("sys_role")
public class Role extends RestEntity<Long> {

    //
    @TableId
    private Long id;
    // 角色名称
    @TableField(value = "role_name")
    private String roleName;
    // 备注
    @TableField(value = "remark")
    private String remark;

    @TableField(exist = false)
    private Long[] menuIds;

    @TableField(exist = false)
    private Long[] roleIds;

    @TableField(exist = false)
    private Boolean flag = false;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
