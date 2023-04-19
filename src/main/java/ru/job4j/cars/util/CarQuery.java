package ru.job4j.cars.util;

public class CarQuery {
    public static final String DELETE = "DELETE FROM Car c WHERE c.id = :fId";
    public static final String FIND_ALL = """
            SELECT DISTINCT c
            FROM Car c
            LEFT JOIN FETCH c.owners
            ORDER BY c.id ASC
            """;

    public static final String FIND_BY_ID = """
            SELECT DISTINCT c
            FROM Car c
            LEFT JOIN FETCH c.owners
            WHERE c.id = :fId
            ORDER BY c.id ASC
            """;

}
