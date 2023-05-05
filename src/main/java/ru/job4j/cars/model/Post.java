package ru.job4j.cars.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne()
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "USER_ID_FK"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id", foreignKey = @ForeignKey(name = "CAR_ID_FK"))
    private Car car;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_id")
    private List<PriceHistory> priceHistories = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "participates",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<Post> participates = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "file_id", foreignKey = @ForeignKey(name = "FILE_ID_FK"))
    private File file;

}