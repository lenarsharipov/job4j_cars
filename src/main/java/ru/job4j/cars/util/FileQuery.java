package ru.job4j.cars.util;

public class FileQuery {

    public static final String FIND_BY_ID = "FROM File f WHERE f.id = :fId";

    public static final String DELETE =
            "DELETE FROM File f WHERE f.id = :fId";

}
