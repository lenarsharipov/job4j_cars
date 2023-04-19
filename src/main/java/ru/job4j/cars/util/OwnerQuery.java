package ru.job4j.cars.util;

public class OwnerQuery {
    public static final String DELETE = "DELETE FROM Owner o WHERE o.id = :fId";
    public static final String FIND_ALL = "FROM Owner o ORDER BY o.id ASC";
    public static final String FIND_BY_ID = "FROM Owner o WHERE o.id = :fId";
}
