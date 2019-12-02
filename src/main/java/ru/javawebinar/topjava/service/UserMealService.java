package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.util.Collection;
import java.util.List;

/**
 * Сервис для работы с едой
 */
public interface UserMealService {

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
     * @throws NullPointerException нет такой записи в бд
     */
    UserMealWithExceed update(long id, UserMealWithExceed userMeal) throws NullPointerException;

    /**
     * Удалить по id
     *
     * @throws NullPointerException нет такой записи в бд
     */
    UserMealWithExceed delete(long id) throws NullPointerException;
}
