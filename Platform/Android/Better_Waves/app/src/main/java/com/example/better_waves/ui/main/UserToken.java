package com.example.better_waves.ui.main;

import java.util.HashMap;
import java.util.Map;

public class UserToken {
    static String token;
    static Map<String, String> headerToken;
    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        UserToken.token = token;
    }

    public static Map<String, String> getHeaderToken() {
        headerToken = new HashMap<>();
        headerToken.put("Authorization", "Token " + UserToken.getToken());
        return headerToken;
    }
}
