package com.example.laba3_3;

public class ServiceLocator {
    private final DirManager pathReader;
    private final Validator validator;
    private final IUserRepository repository;
    private final AccountService accountService;

    public ServiceLocator() {
        pathReader = new DirManager();
        validator = new Validator();
//        repository = new H2UserRepository();
        repository = new UserRepositoryDictionary();
        accountService = new AccountService(repository);
    }

    public DirManager getPathReader() {
        return pathReader;
    }

    public Validator getValidator() {
        return validator;
    }

    public IUserRepository getUserRepository() {
        return repository;
    }

    public AccountService getAccountService() {
        return accountService;
    }
}
