package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "generation")
@Data
public class Generation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
