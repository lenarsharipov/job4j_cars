package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cylinder")
@Data
public class Cylinder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "count", nullable = false, unique = true)
    private String count;
}
