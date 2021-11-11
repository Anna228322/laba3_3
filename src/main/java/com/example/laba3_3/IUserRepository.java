package com.example.laba3_3;

import javax.servlet.http.HttpSession;

public interface IUserRepository {
    void addUser(User user);

    void addSession(String userId, HttpSession session);

    User getUserBySessionId(String userId);

    User getUserByLogin(String login);

    User getUserByEmail(String email);

    boolean isSessionRegistered(String userId);

    void cleanUserSession(String userId);
}
