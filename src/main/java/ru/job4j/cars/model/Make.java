package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "make")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Make {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    @EqualsAndHashCode.Include
    private String name;

    @ManyToMany
    @JoinTable(
            name = "make_model",
            joinColumns = {@JoinColumn(name = "make_id")},
            inverseJoinColumns = {@JoinColumn(name = "model_id")})
    private List<Model> models = new ArrayList<>();

}
