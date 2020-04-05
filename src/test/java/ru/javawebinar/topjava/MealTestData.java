package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_USER_ID = START_SEQ + 2;
    public static final int MEAL_ADMIN_ID = START_SEQ + 3;

    public static final Meal MEAL_USER = new Meal(MEAL_USER_ID, LocalDateTime.now(), "Еда_юзера", 200);
    public static final Meal MEAL_ADMIN = new Meal(MEAL_ADMIN_ID, LocalDateTime.now(), "Еда_админа", 400);
    static {
        MEAL_USER.setUserId(USER_ID);
        MEAL_ADMIN.setUserId(ADMIN_ID);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dateTime");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dateTime").isEqualTo(expected);
    }
}
