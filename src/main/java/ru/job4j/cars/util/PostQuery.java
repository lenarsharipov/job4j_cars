package ru.job4j.cars.util;

public class PostQuery {
    public static final String DELETE = "DELETE FROM Post WHERE id = :fId";
    public static final String FIND_ALL = """
            SELECT DISTINCT p
            FROM Post p
            LEFT JOIN FETCH p.priceHistories
            LEFT JOIN FETCH p.participates
            ORDER BY id ASC
            """;

    public static final String FIND_BY_ID = """
            SELECT DISTINCT p
            FROM Post p
            LEFT JOIN FETCH p.priceHistories
            LEFT JOIN FETCH p.participates
            WHERE id = :fId
            ORDER BY id ASC
            """;
}
