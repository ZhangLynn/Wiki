package com.ccb.wiki.req;

import javax.validation.constraints.NotNull;

public class UserResetPwdReq {

    @NotNull(message = "【用户ID】不能为空")
    private Long id;

    @NotNull(message = "【用户密码】不能为空")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserResetPwdReq{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}