package com.example.laba3_3;

public interface IValidator {
    boolean validatePath(String path);

    RegistrationResult validateNewUser(UserProfile user);
}
