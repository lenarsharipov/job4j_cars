package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "make_model_generation")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MakeModelGeneration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne
    @JoinColumn(
            name = "make_model_id",
            foreignKey = @ForeignKey(name = "MAKE_MODEL_ID_FK"),
            nullable = false)
    private MakeModel makeModel;

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
            foreignKey = @ForeignKey(name = "END_YEAR_ID_FK"))
    private Year endYear;

}
