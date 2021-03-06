package com.example.laba3_3;

public class Validator {
    public boolean validatePath(String path) {
        return path != null && !path.equals("");
    }

    public RegistrationResult validateNewUser(User user) {
        boolean fieldsAreFilled = user.getLogin() != null &&
                user.getPassword() != null &&
                user.getEmail() != null;
        if (!fieldsAreFilled)
            return RegistrationResult.FIELDS_ARE_EMPTY;

        boolean loginIsValid = user.getLogin().length() > 2 && !user.getLogin().contains("@");
        boolean passwordIsValid = user.getPassword().length() > 3;
        boolean emailIsValid = user.getEmail().split("@").length == 2 &&
                user.getEmail().split("\\.").length == 2 &&
                user.getEmail().split(" ").length == 1 &&
                user.getEmail().split("@")[0].length() > 0 &&
                user.getEmail().split("@")[1].split("\\.")[0].length() > 0 &&
                user.getEmail().split("\\.")[1].length() > 0;

        if (!loginIsValid)
            return RegistrationResult.INVALID_LOGIN;
        if (!passwordIsValid)
            return RegistrationResult.INVALID_PASSWORD;
        if (!emailIsValid)
            return RegistrationResult.INVALID_EMAIL;
        return RegistrationResult.OK;
    }
}
