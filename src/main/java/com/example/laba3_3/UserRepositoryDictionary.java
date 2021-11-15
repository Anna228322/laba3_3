package com.example.laba3_3;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryDictionary implements IUserRepository {
    private final static HashMap<String, User> userStorage = new HashMap<>();
    private final static HashMap<String, HttpSession> sessionsStorage = new HashMap<>();

    @Override
    public void addUser(User user) {
        userStorage.put(user.getId(), user);
    }

    @Override
    public void addSession(String userId, HttpSession session) {
        sessionsStorage.put(userId, session);
    }

    @Override
    public User getUserBySessionId(String sessionId) {
        if (!isSessionRegistered(sessionId))
            return null;
        String userId = sessionsStorage.entrySet().stream()
                .filter(entry -> entry.getValue().getId().equals(sessionId))
                .findFirst()
                .map(Map.Entry::getKey).orElse(null);
        if (userId == null)
            return null;
        if (userStorage.containsKey(userId))
            return userStorage.get(userId);
        return null;
    }

    @Override
    public User getUserByLogin(String login) {
        Optional<User> User = userStorage.values().stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
        return User.orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> User = userStorage.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
        return User.orElse(null);
    }

    @Override
    public boolean isSessionRegistered(String sessionId) {
        Optional<HttpSession> foundSession = sessionsStorage.values().stream()
                .filter(session -> session.getId().equals(sessionId))
                .findFirst();
        return foundSession.isPresent();
    }

    @Override
    public void cleanUserSession(String userId) {
        sessionsStorage.remove(userId);
    }
}
