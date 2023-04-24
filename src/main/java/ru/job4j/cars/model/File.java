package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class File {
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    private String name;
    @NonNull
    private String path;

}