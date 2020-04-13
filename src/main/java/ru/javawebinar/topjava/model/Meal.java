package ru.javawebinar.topjava.model;


import org.hibernate.validator.constraints.Range;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Entity
@Table(name = "meals",
        uniqueConstraints = {@UniqueConstraint(name = "meals_unique_user_datetime_idx", columnNames = {"user_id", "date_time"})})
@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m where m.id = :id AND m.userId = :user_id"),
        @NamedQuery(name = Meal.GET_BETWEEN_DATES, query = "SELECT m FROM Meal m LEFT JOIN FETCH m.user where m.dateTime >= :startDate AND m.dateTime <= :endDate"),
        @NamedQuery(name = Meal.GET_BY_ID_AND_USER, query = "SELECT m FROM Meal m LEFT JOIN FETCH m.user where m.id = :id AND m.userId = :user_id"),
        @NamedQuery(name = Meal.GET_ALL_SORTED, query = "SELECT m FROM Meal m ORDER BY m.userId, m.calories"),
})
public class Meal extends AbstractBaseEntity {

    public static final String DELETE = "Meal.delete";
    public static final String GET_BETWEEN_DATES = "Meal.getBetweenDates";
    public static final String GET_BY_ID_AND_USER = "Meal.getByIDAndUser";
    public static final String GET_ALL_SORTED = "Meal.getAllSorted";

    @Column(name = "date_time", nullable = false)
    @javax.validation.constraints.NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank
    private String description;

    @Column(name = "calories", nullable = false)
    @Range(min = 0, max = 10000)
    private int calories;

    @Transient
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Meal() {
    }

    public Meal(@NotNull LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, @NotNull LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
