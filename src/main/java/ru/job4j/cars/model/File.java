package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "file")
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @NonNull
    @EqualsAndHashCode.Include
    @Column(name = "path", nullable = false, unique = true)
    private String path;

}