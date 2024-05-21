package svk.transrest.core.car;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import svk.transrest.core.Vehicle;
import svk.transrest.core.enums.BodyType;
import svk.transrest.core.enums.FuelType;
import svk.transrest.core.picture.Picture;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Purpose:
 * The Car class represents a specific type of vehicle - a car, with additional attributes and functionalities.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "cars")
@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class Car extends Vehicle {

    @Enumerated(EnumType.STRING)
    private BodyType bodyType;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @OneToMany(mappedBy = "car", orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Picture> pictures = new ArrayList<>();

    public Car(Long id, String make, String model, Integer price, Short yearOfRegistration, Integer mileage, String color, LocalDateTime createdAt, LocalDateTime updateAt, BodyType bodyType, FuelType fuelType, List<Picture> pictures) {
        super(id, make, model, price, yearOfRegistration, mileage, color, createdAt, updateAt);
        this.bodyType = bodyType;
        this.fuelType = fuelType;
        this.pictures = pictures;
    }
}
