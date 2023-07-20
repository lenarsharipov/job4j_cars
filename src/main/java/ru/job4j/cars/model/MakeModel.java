package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "make_model")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MakeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne
    @JoinColumn(
            name = "model_id",
            foreignKey = @ForeignKey(name = "MODEL_ID_FK"),
            nullable = false
    )
    private Model model;

    @ManyToOne
    @JoinColumn(
            name = "make_id",
            foreignKey = @ForeignKey(name = "MAKE_ID_FK"),
            nullable = false
    )
    private Make make;

}
