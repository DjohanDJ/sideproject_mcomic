package com.example.m_comic.authentications;

import com.example.m_comic.models.User;

public class UserSession {

    private static User currentUser = null;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        UserSession.currentUser = currentUser;
    }
}
