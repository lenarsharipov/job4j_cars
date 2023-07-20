package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "modification")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Modification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne
    @JoinColumn(
            name = "make_id",
            foreignKey = @ForeignKey(name = "MAKE_ID_FK"),
            nullable = false)
    private Make make;

    @ManyToOne
    @JoinColumn(
            name = "model_id",
            foreignKey = @ForeignKey(name = "MODEL_ID_FK"),
            nullable = false)
    private Model model;

    @ManyToOne
    @JoinColumn(
            name = "generation_id",
            foreignKey = @ForeignKey(name = "GENERATION_ID_FK"),
            nullable = false)
    private Generation generation;

    @ManyToOne
    @JoinColumn(
            name = "start_year_id",
            foreignKey = @ForeignKey(name = "START_YEAR_ID_FK"),
            nullable = false)
    private Year startYear;

    @ManyToOne
    @JoinColumn(
            name = "end_year_id",
            foreignKey = @ForeignKey(name = "END_YEAR_ID_FK")
    )
    private Year endYear;

    @ManyToOne
    @JoinColumn(
            name = "trim_id",
            foreignKey = @ForeignKey(name = "TRIM_ID_FK"),
            nullable = false)
    private Trim trim;

    @ManyToOne
    @JoinColumn(
            name = "body_style_id",
            foreignKey = @ForeignKey(name = "BODY_STYLE_ID_FK"),
            nullable = false)
    private BodyStyle bodyStyle;

    @ManyToOne
    @JoinColumn(
            name = "engine_id",
            foreignKey = @ForeignKey(name = "ENGINE_ID_FK"),
            nullable = false)
    private Engine engine;

    @ManyToOne
    @JoinColumn(
            name = "door_id",
            foreignKey = @ForeignKey(name = "DOOR_ID_FK"),
            nullable = false)
    private Door door;

    @ManyToOne
    @JoinColumn(
            name = "transmission_id",
            foreignKey = @ForeignKey(name = "TRANSMISSION_ID_FK"),
            nullable = false)
    private Transmission transmission;

    @ManyToOne
    @JoinColumn(
            name = "drivetrain_id",
            foreignKey = @ForeignKey(name = "DRIVETRAIN_ID_FK"),
            nullable = false)
    private Drivetrain drivetrain;
}
