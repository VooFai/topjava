package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.util.Collection;

public interface UserMealDao {

    /**
     * Create entity in db
     *
     * @param userMeal entity
     */
    UserMealWithExceed create(UserMealWithExceed userMeal);

    /**
     * Get entity from db
     *
     * @param id entity Id
     */
    UserMealWithExceed get(long id) throws NullPointerException;

    /**
     * @return all the entities in db
     */
    Collection<UserMealWithExceed> getAll();

    /**
     * Update entity in db
     *
     * @param id       entity to update
     * @param userMeal new info
     * @return result
     */
    UserMealWithExceed update(long id, UserMealWithExceed userMeal) throws NullPointerException;

    /**
     * Удалить по id
     */
    UserMealWithExceed delete(long id) throws NullPointerException;
}
