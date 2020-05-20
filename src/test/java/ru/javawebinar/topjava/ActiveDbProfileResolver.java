package ru.javawebinar.topjava;

import org.jetbrains.annotations.NotNull;
import org.springframework.test.context.ActiveProfilesResolver;

//http://stackoverflow.com/questions/23871255/spring-profiles-simple-example-of-activeprofilesresolver
public class ActiveDbProfileResolver implements ActiveProfilesResolver {

    @NotNull
    @Override
    public String[] resolve(@NotNull Class<?> aClass) {
//        return new String[]{Profiles.getActiveDbProfile()};
        return new String[]{Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION};
    }
}