package edu.csu.JustMusic.musicplayer.model;

public class User {
    //无参构造函数
    public User() {
    }
    //有参构造函数
    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    //用户账号和用户密码
    public String account;
    public String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
