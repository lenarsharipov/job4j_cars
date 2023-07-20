package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "car")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "mileage", nullable = false)
    private Integer mileage;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @ManyToOne
    @JoinColumn(
            name = "year_id",
            foreignKey = @ForeignKey(name = "YEAR_ID_FK"),
            nullable = false)
    private Year year;

    @ManyToOne
    @JoinColumn(
            name = "condition_id",
            foreignKey = @ForeignKey(name = "CONDITION_ID_FK"),
            nullable = false)
    private Condition condition;

    @ManyToOne
    @JoinColumn(
            name = "color_id",
            foreignKey = @ForeignKey(name = "COLOR_ID_FK"),
            nullable = false)
    private Color color;

    @ManyToOne
    @JoinColumn(
            name = "owner_count_id",
            foreignKey = @ForeignKey(name = "OWNER_COUNT_ID_FK"),
            nullable = false)
    private OwnerCount ownerCount;

    @ManyToOne
    @JoinColumn(
            name = "modification_id",
            foreignKey = @ForeignKey(name = "MODIFICATION_ID_FK"),
            nullable = false)
    private Modification modification;

}
