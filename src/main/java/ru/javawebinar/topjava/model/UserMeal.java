package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class UserMeal extends Entity {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    public UserMeal(Long id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }
}


