package com.example.laba3_3;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpSession;

public class UserDao {
    private final Session session;

    public UserDao(Session session) {
        this.session = session;
    }

    public void add(User user) {
        session.beginTransaction();
        session.save(mapToDataSet(user));
        session.getTransaction().commit();
    }

    public void attachSession(String userId, HttpSession httpSession) {
        User profile = getById(userId);
        UserDataSet dataSet = mapToDataSet(profile);
        dataSet.setSessionId(httpSession.getId());
        session.beginTransaction();
        session.merge(dataSet);
        session.getTransaction().commit();
    }

    public User getById(String userId) {
        System.out.println(userId);
        return mapToProfile(session.get(UserDataSet.class, userId));
    }

    public User getByLogin(String login) {
        Criteria criteria = session.createCriteria(UserDataSet.class);
        return mapToProfile(((UserDataSet) criteria.add(Restrictions.eq("login", login)).uniqueResult()));
    }

    public User getByEmail(String email) {
        Criteria criteria = session.createCriteria(UserDataSet.class);
        return mapToProfile(((UserDataSet) criteria.add(Restrictions.eq("email", email)).uniqueResult()));
    }

    public User getBySessionId(String sessionId) {
        Criteria criteria = session.createCriteria(UserDataSet.class);
        return mapToProfile(((UserDataSet) criteria.add(Restrictions.eq("sessionId", sessionId)).uniqueResult()));
    }

    public boolean isSessionExists(String sessionId) {
        getBySessionId(sessionId);
        return getBySessionId(sessionId) != null;
    }

    public void detachSession(String userId) {
        User profile = getById(userId);
        UserDataSet dataSet = mapToDataSet(profile);
        if (dataSet == null)
            return;
        dataSet.setSessionId(null);
        session.beginTransaction();
        session.merge(dataSet);
        session.getTransaction().commit();
    }

    private User mapToProfile(UserDataSet dataSet) {
        return dataSet == null ? null : new User(
                dataSet.getId(),
                dataSet.getLogin(),
                dataSet.getPassword(),
                dataSet.getEmail()
        );
    }

    private UserDataSet mapToDataSet(User profile) {
        return profile == null ? null : new UserDataSet(
                profile.getId(),
                profile.getLogin(),
                profile.getPassword(),
                profile.getEmail()
        );
    }
}
