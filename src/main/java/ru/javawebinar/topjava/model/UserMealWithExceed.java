package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class UserMealWithExceed extends UserMeal {
    private final boolean exceed;

    public UserMealWithExceed(Long id, LocalDateTime dateTime, String description, int calories, boolean exceed) {
        super(id, dateTime, description, calories);
        this.exceed = exceed;
    }

    @Override
    public String toString() {
        return "UserMealWithExceed{" +
                "dateTime=" + this.getDateTime() +
                ", description='" + this.getDescription() + '\'' +
                ", calories=" + this.getCalories() +
                ", exceed=" + exceed +
                '}';
    }

    public boolean isExceed() {
        return exceed;
    }
}
