package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "price_history")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class PriceHistory {

    private static final DateTimeFormatter FORMATTER
            = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @EqualsAndHashCode.Include
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ToString.Include
    private long before;

    @ToString.Include
    private long after;

    private LocalDateTime created = LocalDateTime.now();

    @ToString.Include
    public String created() {
        return FORMATTER.format(created);
    }
}