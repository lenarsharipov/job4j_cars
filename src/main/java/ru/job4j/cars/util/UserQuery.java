package ru.job4j.cars.util;

public class UserQuery {
    public static final String DELETE = "DELETE FROM User u WHERE u.id = :fId";
    public static final String FIND_ALL = "FROM User u ORDER BY u.id ASC";
    public static final String FIND_BY_ID = "FROM User u WHERE u.id = :fId";
    public static final String FIND_BY_LIKE_LOGIN = "FROM User u WHERE u.login LIKE :fKey";
    public static final String FIND_BY_LOGIN = "FROM User u WHERE u.login = :fLogin";
}
