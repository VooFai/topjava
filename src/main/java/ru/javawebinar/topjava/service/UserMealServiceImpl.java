package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.dao.UserMealDaoImpl;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.util.Collection;

public class UserMealServiceImpl implements UserMealService {
    private static UserMealServiceImpl instance = new UserMealServiceImpl();
    private static UserMealDaoImpl dao = UserMealDaoImpl.getInstance();

    private UserMealServiceImpl() {
    }

    public static UserMealServiceImpl getInstance() {
        return instance;
    }

    @Override
    public UserMealWithExceed create(UserMealWithExceed userMeal) {
        return dao.create(userMeal);
    }

    @Override
    public UserMealWithExceed get(long id) throws NullPointerException {
        return dao.get(id);
    }

    @Override
    public Collection<UserMealWithExceed> getAll() {
        return dao.getAll();
    }

    @Override
    public UserMealWithExceed update(long id, UserMealWithExceed userMeal) throws NullPointerException {
        return dao.update(id, userMeal);
    }

    @Override
    public UserMealWithExceed delete(long id) throws NullPointerException {
        return dao.delete(id);
    }
}
