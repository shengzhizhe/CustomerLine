package org.client.com.login.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class LoginModel implements Serializable {

    @NotBlank(message = "账户不能为空")
    @Size(message = "密码长度为8-20", min = 8, max = 20)
    @Email(message = "不是标准的email格式")
    private String username;

    @NotBlank(message = "密码不能为空，并且不能包含空格")
    @Size(message = "密码长度为8-20", min = 8, max = 20)
    @Pattern(regexp = "[a-zA-Z0-9]*", message = "密码由数字和英文字母组成,必须含有大小写")
    private String password;

    private String yzm;
//0:user 1:cus 2:admin
    private int types;
    //商家编码
    private String coding;

    public LoginModel() {
        super();
    }

    public LoginModel(String username, String password, String yzm, int types, String coding) {
        this.username = username;
        this.password = password;
        this.yzm = yzm;
        this.types = types;
        this.coding = coding;
    }

    public int getTypes() {
        return types;
    }

    public void setTypes(int types) {
        this.types = types;
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

    public String getYzm() {
        return yzm;
    }

    public void setYzm(String yzm) {
        this.yzm = yzm;
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", yzm='" + yzm + '\'' +
                ", types=" + types +
                ", coding='" + coding + '\'' +
                '}';
    }
}
