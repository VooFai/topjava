package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.*;
import java.util.stream.Collectors;


public class UserUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(1, "me", "email", "password", Role.ROLE_ADMIN)
    );

    public static List<User> getSorted(Collection<User> users) {
        return users.stream()
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());
    }

}