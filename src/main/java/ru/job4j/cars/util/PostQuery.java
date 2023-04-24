package ru.job4j.cars.util;

public class PostQuery {

    public static final String DELETE =
            "DELETE FROM Post p WHERE p.id = :fId";

    public static final String FIND_ALL = """
            SELECT DISTINCT p
            FROM Post p
            LEFT JOIN FETCH p.priceHistories
            LEFT JOIN FETCH p.participates
            ORDER BY p.id ASC
            """;

    public static final String FIND_BY_ID = """
            SELECT DISTINCT p
            FROM Post p
            LEFT JOIN FETCH p.priceHistories
            LEFT JOIN FETCH p.participates
            WHERE p.id = :fId
            ORDER BY p.id ASC
            """;

    public static final String FIND_BY_TODAY = """
            SELECT DISTINCT p
            FROM Post p
            LEFT JOIN FETCH p.priceHistories
            LEFT JOIN FETCH p.participates
            WHERE p.created >= :fCreated
            ORDER BY p.id ASC
            """;

    public static final String FIND_ALL_WITH_PHOTO = """
            SELECT DISTINCT p
            FROM Post p
            LEFT JOIN FETCH p.priceHistories
            LEFT JOIN FETCH p.participates
            LEFT JOIN FETCH p.files
            WHERE p.hasPhoto = TRUE
            ORDER BY p.id ASC
            """;

    public static final String FIND_BY_MAKE = """
            SELECT DISTINCT p
            FROM Post p
            LEFT JOIN FETCH p.priceHistories
            LEFT JOIN FETCH p.participates
            LEFT JOIN FETCH p.files
            WHERE p.car.make = :fMake
            ORDER BY p.id ASC
            """;
}
