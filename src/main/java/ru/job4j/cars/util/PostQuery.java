package ru.job4j.cars.util;

public class PostQuery {

    public static final String DELETE =
            "DELETE FROM Post p WHERE p.id = :fId";

    public static final String FIND_ALL = """
            SELECT DISTINCT p
            FROM Post p
            ORDER BY p.id ASC
            """;

    public static final String FIND_BY_ID = """
            SELECT DISTINCT p
            FROM Post p
            WHERE p.id = :fId
            ORDER BY p.id ASC
            """;

    public static final String FIND_BY_TODAY = """
            SELECT DISTINCT p
            FROM Post p
            WHERE p.created >= :fCreated
            ORDER BY p.id ASC
            """;

    public static final String FIND_ALL_WITH_PHOTO = """
            SELECT DISTINCT p
            FROM Post p
            LEFT JOIN FETCH p.file
            WHERE p.file IS NOT NULL
            ORDER BY p.id ASC
            """;

    public static final String FIND_BY_MAKE = """
            SELECT DISTINCT p
            FROM Post p
            LEFT JOIN FETCH p.car
            WHERE p.car.make.name = :fName
            ORDER BY p.id ASC
            """;
}
