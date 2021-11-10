package com.example.laba3_3;

import javax.servlet.http.HttpSession;

public interface IUserRepository {
    void addUser(UserProfile user);

    void addSession(String userId, HttpSession session);

    UserProfile getUserBySessionId(String userId);

    UserProfile getUserByLogin(String login);

    UserProfile getUserByEmail(String email);

    boolean isSessionRegistered(String userId);

    void cleanUserSession(String userId);
}
