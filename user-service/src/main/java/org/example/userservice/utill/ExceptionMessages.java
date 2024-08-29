package org.example.userservice.utill;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionMessages {

    public static final String USER_NOT_FOUND = "User with id '%s' not found";

    public static final String USER_ALREADY_EXIST_BY_PHONE = "User with phone '%s' already exist";
}
