package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "price_history")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PriceHistory {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long before;

    private Long after;

    private LocalDateTime created = LocalDateTime.now();

}