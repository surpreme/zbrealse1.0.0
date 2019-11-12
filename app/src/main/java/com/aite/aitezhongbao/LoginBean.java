package com.aite.aitezhongbao;

public class LoginBean {
    private String userName;
    private String userNo;

    public LoginBean(String userName, String userNo) {
        this.userName = userName;
        this.userNo = userNo;
    }

    public LoginBean() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "userName='" + userName + '\'' +
                ", userNo='" + userNo + '\'' +
                '}';
    }
}
