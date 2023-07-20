package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "engine_capacity")
@Data
public class EngineCapacity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "litres", nullable = false, unique = true)
    private String litres;
}
