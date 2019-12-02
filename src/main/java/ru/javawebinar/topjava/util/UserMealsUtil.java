package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(1L, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(2L, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(3L, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(4L, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(5L, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(6L, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> filteredWithExceeded = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        filteredWithExceeded.forEach(System.out::println);
        //        .toLocalDate();
//        .toLocalTime();
    }

    /**
     * @return Только входящие в рейндж по датам (на стримах)
     */
    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDay = mealList.stream().collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(),
                Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExceed(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        caloriesSumByDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(toList());
    }


    /**
     * @return Только входящие в рейндж по датам (на циклах)
     */
    public static List<UserMealWithExceed> getFilteredListWithExceededByCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesPerSumPerDay = new HashMap<>();
        for (UserMeal meal : mealList) {
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            int calories = caloriesPerSumPerDay.getOrDefault(mealDate, 0) + meal.getCalories();
            caloriesPerSumPerDay.put(mealDate, calories);
        }


        List<UserMealWithExceed> result = new ArrayList<>();
        Predicate<UserMeal> dateIsInRange = (um) -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime);
        for (UserMeal meal : mealList) {
            LocalDateTime mealDate = meal.getDateTime();
            if (dateIsInRange.test(meal)) {
                result.add(new UserMealWithExceed(meal.getId(), mealDate, meal.getDescription(), meal.getCalories(),
                        caloriesPerSumPerDay.get(mealDate.toLocalDate()) > caloriesPerDay));
            }
        }

        return result;
    }


    /**
     * @return Только входящие в рейндж по датам (стожность Оn)
     */
    public static List<UserMealWithExceed> getFilteredWithExceededInOnePass2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        final class Aggregate {
            private final List<UserMeal> dailyMeals = new ArrayList<>();
            private int dailySumOfCalories;

            private void accumulate(UserMeal meal) {
                dailySumOfCalories += meal.getCalories();
                if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                    dailyMeals.add(meal);
                }
            }

            // never invoked if the upstream is sequential
            private Aggregate combine(Aggregate that) {
                this.dailySumOfCalories += that.dailySumOfCalories;
                this.dailyMeals.addAll(that.dailyMeals);
                return this;
            }

            private Stream<UserMealWithExceed> finisher() {
                final boolean exceed = dailySumOfCalories > caloriesPerDay;
                return dailyMeals.stream().map(meal -> createWithExceed(meal, exceed));
            }
        }

        Collection<Stream<UserMealWithExceed>> values = meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getDateTime,
                        Collector.of(Aggregate::new, Aggregate::accumulate, Aggregate::combine, Aggregate::finisher))
                ).values();

        return values.stream().flatMap(identity()).collect(toList());
    }

    public static UserMealWithExceed createWithExceed(UserMeal meal, boolean exceeded) {
        return new UserMealWithExceed(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }
}
