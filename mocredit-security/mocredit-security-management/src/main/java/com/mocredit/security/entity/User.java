package com.mocredit.security.entity;


import java.io.Serializable;

/**
 * <p>User:
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public class User implements Serializable {
    private Long id; //编号
    private Long organizationId; //所属公司
    private String username; //用户名
    private String password; //密码
    private String md5Pwd;//md5密码
    private String ctime;//创建时间
    private String utime;//更新时间
    private String salt; //加密密码的盐
    private Boolean locked = Boolean.FALSE;
    private String roleName;//角色名称

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        if (ctime != null && ctime.contains(".0")) {
            ctime = ctime.substring(0, ctime.length() - 2);
        }
        this.ctime = ctime;
    }

    public String getMd5Pwd() {
        return md5Pwd;
    }

    public void setMd5Pwd(String md5Pwd) {
        this.md5Pwd = md5Pwd;
    }

    public String getUtime() {
        return utime;
    }

    public void setUtime(String utime) {
        if (utime != null && utime.contains(".0")) {
            utime = utime.substring(0, utime.length() - 2);
        }
        this.utime = utime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCredentialsSalt() {
        return username + salt;
    }


    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;

        return true;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", organizationId=" + organizationId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", ctime='" + ctime + '\'' +
                ", utime='" + utime + '\'' +
                ", salt='" + salt + '\'' +
                ", locked=" + locked +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
