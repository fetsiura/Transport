package svk.transrest.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import svk.transrest.core.enums.BodyType;
import svk.transrest.core.enums.FuelType;

import java.util.List;

/**
 * Purpose:
 * The CarDto class represents a Data Transfer Object (DTO) for car-related data, facilitating data exchange between the client and server in a structured format.
 */
@Schema(description = "CarDto model information")
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarDto {
    private Long id;
    @NotNull(message = "Make must not be null")
    private String make;

    @NotNull(message = "Model must not be null")
    private String model;

    @NotNull(message = "Price must not be null")
    private Integer price;

    @NotNull(message = "YearOfRegistration must not be null")
    private Short yearOfRegistration;

    @NotNull(message = "Mileage must not be null")
    private Integer mileage;

    @NotNull(message = "Color must not be null")
    private String color;

    @NotNull(message = "BodyType must not be null")
    private BodyType bodyType;

    @NotNull(message = "FuelType must not be null")
    private FuelType fuelType;

    private List<PictureDto> pictures;
}
