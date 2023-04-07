package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auto_posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Post {
    private static final DateTimeFormatter FORMATTER
            = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private int id;

    @ToString.Include
    private String description;

    private LocalDateTime created = LocalDateTime.now();

    @ToString.Include
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "price_history")
    private List<PriceHistory> priceHistories = new ArrayList<>();

    @ToString.Include
    public String created() {
        return FORMATTER.format(created);
    }

}