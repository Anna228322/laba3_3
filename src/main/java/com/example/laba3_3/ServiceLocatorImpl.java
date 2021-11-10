package com.example.laba3_3;

public class ServiceLocatorImpl implements IServiceLocator {
    private final ITimeService dateProvider;
    private final IDirManager pathReader;
    private final IValidator validator;
    private final IUserRepository repository;
    private final IAccountService accountService;

    public ServiceLocatorImpl() {
        dateProvider = new TimeServiceImpl();
        pathReader = new DirManagerImpl();
        validator = new ValidatorImpl();
//        repository = new H2UserRepositoryImpl();
        repository = new UserRepositoryRuntimeImpl();
        accountService = new AccountServiceImpl(repository);
    }

    @Override
    public ITimeService getDateProvider() {
        return dateProvider;
    }

    @Override
    public IDirManager getPathReader() {
        return pathReader;
    }

    @Override
    public IValidator getValidator() {
        return validator;
    }

    @Override
    public IUserRepository getUserRepository() {
        return repository;
    }

    @Override
    public IAccountService getAccountService() {
        return accountService;
    }
}
