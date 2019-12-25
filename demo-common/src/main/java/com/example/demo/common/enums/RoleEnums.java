package com.example.demo.common.enums;

/**
 * @author wuhengzhen
 */
public enum RoleEnums {
    /**
     * 管理员角色
     */
    ROLE_ADMIN(0, "admin", "管理员"),
    /**
     * 普通用户
     */
    ROLE_NORMAL(1, "normal", "普通用户"),
    /**
     * 会员用户
     */
    ROLE_VIP(2, "vip", "会员用户");


    private Integer code;

    private String name;

    private String msg;

    RoleEnums(Integer code, String name, String msg) {
        this.code = code;
        this.name = name;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "RoleEnums{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
