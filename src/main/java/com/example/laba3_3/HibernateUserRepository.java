package com.example.laba3_3;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.servlet.http.HttpSession;

public class HibernateUserRepository implements IUserRepository {
    private static final String hibernate_dialect = "org.hibernate.dialect.H2Dialect";
    private static final String hibernate_driver = "org.h2.Driver";
    private static final String hibernate_url = "jdbc:h2:./hibernate_h2_db";
    private static final String hibernate_username = "root";
    private static final String hibernate_password = "root";
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "create";

    private final SessionFactory sessionFactory;

    public HibernateUserRepository() {
        Configuration configuration = getH2Configuration();
        sessionFactory = createSessionFactory(configuration);
    }

    private Configuration getH2Configuration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserDataSet.class);

        configuration.setProperty("hibernate.dialect", hibernate_dialect);
        configuration.setProperty("hibernate.connection.driver_class", hibernate_driver);
        configuration.setProperty("hibernate.connection.url", hibernate_url);
        configuration.setProperty("hibernate.connection.username", hibernate_username);
        configuration.setProperty("hibernate.connection.password", hibernate_password);
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
//        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public void addUser(User user) {
        getDao().add(user);
    }

    @Override
    public void addSession(String userId, HttpSession session) {
        getDao().attachSession(userId, session);
    }

    @Override
    public User getUserBySessionId(String sessionId) {
        return getDao().getBySessionId(sessionId);
    }

    @Override
    public User getUserByLogin(String login) {
        return getDao().getByLogin(login);
    }

    @Override
    public User getUserByEmail(String email) {
        return getDao().getByEmail(email);
    }

    @Override
    public boolean isSessionRegistered(String userId) {
        return getDao().isSessionExists(userId);
    }

    @Override
    public void cleanUserSession(String userId) {
        getDao().detachSession(userId);
    }

    private UserDao getDao() {
        return new UserDao(sessionFactory.openSession());
    }
}
