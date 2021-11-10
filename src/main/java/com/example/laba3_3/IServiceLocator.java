package com.example.laba3_3;

public interface IServiceLocator {
    ITimeService getDateProvider();

    IDirManager getPathReader();

    IValidator getValidator();

    IUserRepository getUserRepository();

    IAccountService getAccountService();
}
