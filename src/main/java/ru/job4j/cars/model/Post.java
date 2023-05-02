package ru.job4j.cars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "post")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    private String description;

    private LocalDateTime created = LocalDateTime.now();

    @OneToOne()
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "USER_ID_FK"))
    private User user;

    @OneToOne()
    @JoinColumn(name = "car_id", foreignKey = @ForeignKey(name = "CAR_ID_FK"))
    private Car car;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private Set<PriceHistory> priceHistories = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "participates",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<Post> participates = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "file_id", foreignKey = @ForeignKey(name = "FILE_ID_FK"))
    private File file;

}