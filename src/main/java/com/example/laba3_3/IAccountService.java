package com.example.laba3_3;

import javax.servlet.http.HttpSession;

public interface IAccountService {
    RegistrationResult registerUser(UserProfile user);

    boolean isUserAuthorized(String sessionId);

    LoginResult logInUser(UserProfile userProfile, HttpSession session);

    boolean logOut(String sessionId);

    UserProfile getUserBySessionId(String userId);
}
