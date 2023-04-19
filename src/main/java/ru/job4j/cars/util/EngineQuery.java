package ru.job4j.cars.util;

public class EngineQuery {
    public static final String DELETE = "DELETE FROM Engine e WHERE e.id = :fId";
    public static final String FIND_ALL = "FROM Engine e ORDER BY e.id ASC";
    public static final String FIND_BY_ID = "FROM Engine e WHERE e.id = :fId";
}
