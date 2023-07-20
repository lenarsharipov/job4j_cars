package ru.job4j.cars.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "engine")
@Data
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(
            name = "fuel_type_id", foreignKey = @ForeignKey(name = "FUEL_TYPE_ID_FK"),
            nullable = false)
    private FuelType fuelType;

    @ManyToOne
    @JoinColumn(
            name = "horsepower_id", foreignKey = @ForeignKey(name = "HORSEPOWER_ID_FK"),
            nullable = false)
    private HorsePower horsePower;

    @ManyToOne
    @JoinColumn(
            name = "engine_capacity_id", foreignKey = @ForeignKey(name = "ENGINE_CAPACITY_ID_FK"),
            nullable = false)
    private EngineCapacity engineCapacity;

    @ManyToOne
    @JoinColumn(
            name = "cylinder_id", foreignKey = @ForeignKey(name = "CYLINDER-ID_FK"),
            nullable = false)
    private Cylinder cylinder;

}