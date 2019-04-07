package com.lyml.demo1.model;


import com.baomidou.mybatisplus.annotations.TableName;

//表名
@TableName("roleuser")
public class RoleUser extends SuperEntity<RoleUser> {
    private Long roleId;
    private String roleName;
    private Long userId;
    private String userName;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
