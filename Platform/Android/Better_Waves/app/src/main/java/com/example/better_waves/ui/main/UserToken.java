package com.example.better_waves.ui.main;

public class UserToken {
    static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        UserToken.token = token;
    }
}
