package ru.javawebinar.topjava.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;
import ru.javawebinar.topjava.View;
import ru.javawebinar.topjava.util.DateTimeUtil;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SuppressWarnings("JpaQlInspection")
@Entity
@Table(name = "meals",
        uniqueConstraints = {@UniqueConstraint(name = "meals_unique_user_datetime_idx", columnNames = {"user_id", "date_time"})})
@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m where m.id = :id AND m.user.id = :user_id"),
        @NamedQuery(name = Meal.GET_BETWEEN_DATES,
                query = "SELECT m FROM Meal m LEFT JOIN FETCH m.user " +
                        "where m.dateTime >= :start_date AND m.dateTime <= :end_date AND m.user.id = :user_id " +
                        "ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.GET_BY_ID_AND_USER, query = "SELECT m FROM Meal m LEFT JOIN FETCH m.user where m.id = :id AND m.user.id = :user_id"),
        @NamedQuery(name = Meal.GET_ALL_SORTED, query = "SELECT m FROM Meal m LEFT JOIN FETCH m.user WHERE m.user.id = :user_id ORDER BY m.dateTime DESC"),
})
public class Meal extends AbstractBaseEntity {

    public static final String DELETE = "Meal.delete";
    public static final String GET_BETWEEN_DATES = "Meal.getBetweenDates";
    public static final String GET_BY_ID_AND_USER = "Meal.getByIDAndUser";
    public static final String GET_ALL_SORTED = "Meal.getAllSorted";

    @Column(name = "date_time", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    @SafeHtml(groups = {View.ValidatedRestUI.class})
    private String description;

    @Column(name = "calories", nullable = false)
    @Range(min = 10, max = 5000)
    @NotNull
    private Integer calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(groups = View.Persist.class)
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

    public void setCalories(Integer calories) {
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
                "id=" + getId() +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
