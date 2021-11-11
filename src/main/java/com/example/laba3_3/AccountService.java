package com.example.laba3_3;

import javax.servlet.http.HttpSession;

public class AccountService {
    private final IUserRepository userRepository;

    public AccountService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public RegistrationResult registerUser(User user) {
        Validator validator = MainServlet.SERVICE_LOCATOR.getValidator();

        User profileByLogin = userRepository.getUserByLogin(user.getLogin());
        User profileByEmail = userRepository.getUserByEmail(user.getEmail());
        if (profileByEmail != null)
            return RegistrationResult.EMAIL_IS_OCCUPIED;
        if (profileByLogin != null)
            return RegistrationResult.LOGIN_IS_OCCUPIED;
        RegistrationResult validationResult = validator.validateNewUser(user);
        if (validationResult != RegistrationResult.OK)
            return validationResult;

        userRepository.addUser(user);
        return RegistrationResult.OK;
    }

    public boolean isUserAuthorized(String sessionId) {
        return userRepository.isSessionRegistered(sessionId);
    }

    public LoginResult logInUser(User inputProfile, HttpSession session) {
        User foundProfile = inputProfile.getLogin() != null ?
                userRepository.getUserByLogin(inputProfile.getLogin()) :
                userRepository.getUserByEmail(inputProfile.getEmail());
        if (foundProfile == null)
            return LoginResult.PROFILE_NOT_FOUND;
        if (inputProfile.getId() == null)
            inputProfile.setId(foundProfile.getLogin());
        if (!foundProfile.getPassword().equals(inputProfile.getPassword()))
            return LoginResult.INCORRECT_PASSWORD;

        userRepository.addSession(inputProfile.getId(), session);
        return LoginResult.OK;
    }

    public boolean logOut(String sessionId) {
        User user = getUserBySessionId(sessionId);
        userRepository.cleanUserSession(user.getId());
        return true;
    }

    public User getUserBySessionId(String sessionId) {
        return userRepository.getUserBySessionId(sessionId);
    }
}
