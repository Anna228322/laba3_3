package com.example.laba3_3;

public class User {
    private final String login;
    private final String password;
    private String id;
    private final String email;

    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public User(String userId, String login, String password, String email) {
        this(login, password, email);
        setId(userId);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }
}
