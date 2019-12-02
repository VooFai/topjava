package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class UserMealDaoImpl implements UserMealDao {
    private static UserMealDaoImpl instance = new UserMealDaoImpl();
    private final AtomicLong id = new AtomicLong(0);
    private ConcurrentHashMap<Long, UserMealWithExceed> db = new ConcurrentHashMap<>();

    private UserMealDaoImpl() {
        List<UserMealWithExceed> mealList = Arrays.asList(
                new UserMealWithExceed(getNextId(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, Math.random() > 0.5),
                new UserMealWithExceed(getNextId(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000, Math.random() > 0.5),
                new UserMealWithExceed(getNextId(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500, Math.random() > 0.5),
                new UserMealWithExceed(getNextId(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000, Math.random() > 0.5),
                new UserMealWithExceed(getNextId(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500, Math.random() > 0.5),
                new UserMealWithExceed(getNextId(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, Math.random() > 0.5)
        );
        mealList.forEach(meal -> db.put(meal.getId(), meal));
    }

    private Long getNextId() {
        return id.incrementAndGet();
    }


    public static UserMealDaoImpl getInstance() {
        return instance;
    }

    /**
     * @return Возвращает уникальный id записи в БД
     */
    public long getUniqueId() {
        return id.incrementAndGet();
    }


    /**
     * Create entity in db
     *
     * @param userMeal entity
     */
    public UserMealWithExceed create(UserMealWithExceed userMeal) {

        //проставить id
        if (userMeal.getId() == null) {
            userMeal.setId(id.incrementAndGet());
        }

        return db.put(userMeal.getId(), userMeal);
    }

    /**
     * Get entity from db
     *
     * @param id entity Id
     */
    public UserMealWithExceed get(long id) throws NullPointerException {
        return db.get(id);
    }

    @Override
    public Collection<UserMealWithExceed> getAll() {
        return db.values();
    }

    /**
     * Update entity in db
     *
     * @param id       entity to update
     * @param userMeal new info
     * @return result
     */
    public UserMealWithExceed update(long id, UserMealWithExceed userMeal) throws NullPointerException {
        userMeal.setId(id);
        return db.replace(id, userMeal);
    }

    /**
     * Удалить по id
     */
    public UserMealWithExceed delete(long id) throws NullPointerException {
        return db.remove(id);
    }
}
