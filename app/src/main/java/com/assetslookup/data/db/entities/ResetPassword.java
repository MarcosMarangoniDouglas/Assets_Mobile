package com.assetslookup.data.db.entities;

public class ResetPassword {
    private String password;

    public ResetPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
