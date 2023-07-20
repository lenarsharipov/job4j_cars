package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "model")
@Data
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "model_body_style",
            joinColumns = {@JoinColumn(name = "model_id")},
            inverseJoinColumns = {@JoinColumn(name = "body_style_id")})
    private List<BodyStyle> bodyStyles = new ArrayList<>();
}