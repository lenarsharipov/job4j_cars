package ru.job4j.cars.util;

public class PriceHistoryQuery {

    public static final String DELETE = "DELETE FROM PriceHistory p WHERE p.id = :fId";
    public static final String FIND_ALL = "FROM PriceHistory p ORDER BY p.id ASC";
    public static final String FIND_BY_ID = "FROM PriceHistory p WHERE p.id = :fId";
}
