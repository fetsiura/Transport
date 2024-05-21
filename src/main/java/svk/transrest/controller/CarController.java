package svk.transrest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import svk.transrest.core.car.CarServiceImpl;
import svk.transrest.exception.ValidationErrorHandler;
import svk.transrest.payload.CarDto;
import svk.transrest.payload.response.Response;
import svk.transrest.utils.AppConstance;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cars")
@Tag(name = "CRUD REST APIs for Car resource")
public class CarController {

    private final CarServiceImpl carService;

    /**
     * Endpoint: GET /api/cars
     * Description: Retrieve a list of all cars.
     * Request Parameters:
     * page (optional): Page number of the results. Default: 0.
     *
     * @param page     sortBy (optional): Field by which the sorting is done. Default: "made".
     * @param sortBy   sortDir (optional): Sorting direction (ASC or DESC). Default: "ASC".
     * @param sortDir  size (optional): Number of records per page. Default: 10.
     * @param quantity HTTP Status:
     *                 200 OK: Everything worked as expected.
     *                 500 INTERNAL SERVER ERROR: Server error.
     */
    @Operation(summary = "Get Cars REST API", description = "Get Cars REST API is used to retrieve collection of cars from database")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @GetMapping
    public ResponseEntity<Response<CarDto>> getCars(@RequestParam(value = "page", defaultValue = AppConstance.DEFAULT_PAGE, required = false) int page,
                                                    @RequestParam(value = "sortBy", defaultValue = AppConstance.DEFAULT_SORT_BY, required = false) String sortBy,
                                                    @RequestParam(value = "sortDir", defaultValue = AppConstance.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
                                                    @RequestParam(value = "size", defaultValue = AppConstance.DEFAULT_QUANTITY, required = false) int quantity
    ) {
        Response<CarDto> response = carService.getCars(page, quantity, sortBy, sortDir);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Endpoint: GET /api/cars/{id}
     * Description: Retrieve information about a car by its identifier.
     * Request Parameters:
     * id: Identifier of the car.
     *
     * @param id HTTP Status:
     *           200 OK: Everything worked as expected.
     *           404 NOT FOUND: Car with the specified identifier not found.
     *           500 INTERNAL SERVER ERROR: Server error.
     */
    @Operation(summary = "Get Car REST API", description = "Get Car REST API is used to retrieve single car from database")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @GetMapping("/{id}")
    public ResponseEntity<Response<CarDto>> getCarById(@PathVariable Long id) {
        Response<CarDto> response = carService.getCarById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Endpoint: POST /api/cars
     * Description: Create a new car.
     * Body: @param CarDto object containing data about the new car.
     *
     * @param carDto HTTP Status:
     *               201 CREATED: Car successfully created.
     *               400 BAD REQUEST: Error in input data.
     *               500 INTERNAL SERVER ERROR: Server error.
     */
    @Operation(summary = "Create Car REST API", description = "Create Car REST API is used to save car into database")
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @SecurityRequirement(name = "Bear authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Object> createCar(@Valid @RequestBody CarDto carDto, BindingResult result) {
        ResponseEntity<Object> validationErrors = ValidationErrorHandler.handleValidationErrors(result);
        if (validationErrors != null) return validationErrors;
        Response<CarDto> response = carService.createCar(carDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Endpoint: PUT /api/cars/{id}
     * Description: Update information about a car by its identifier.
     * Body: CarDto object containing new data about the car.
     * Request Parameters:
     * id: Identifier of the car.
     *
     * @param carDto
     * @param id     HTTP Status:
     *               200 OK: Everything worked as expected.
     *               400 BAD REQUEST: Error in input data.
     *               404 NOT FOUND: Car with the specified identifier not found.
     *               500 INTERNAL SERVER ERROR: Server error.
     */
    @Operation(summary = "Update Car REST API", description = "Update Car REST API is used to update particular car in the database")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @SecurityRequirement(name = "Bear authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Response<CarDto>> updateCar(@RequestBody CarDto carDto, @PathVariable(name = "id") Long id) {
        Response<CarDto> response = carService.updateCar(carDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Endpoint: DELETE /api/cars/{id}
     * Description: Delete a car by its identifier.
     * Request Parameters:
     * id: Identifier of the car.
     *
     * @param id HTTP Status:
     *           200 OK: Everything worked as expected.
     *           404 NOT FOUND: Car with the specified identifier not found.
     *           500 INTERNAL SERVER ERROR: Server error.
     */
    @Operation(summary = "Delete Car REST API", description = "Delete Car REST API is used to remove particular car from database")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @SecurityRequirement(name = "Bear authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<CarDto>> deleteCar(@PathVariable(name = "id") Long id) {
        Response<CarDto> response = new Response<>();
        carService.deleteCarById(id);
        response.setInfo("Car with id " + id + " successfully deleted");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Endpoint: DELETE /api/cars/{elementId}/pictures/{pictureId}
     * Description: Delete an image by its identifier and element identifier.
     * Request Parameters:
     * elementId: Element identifier.
     *
     * @param elementId pictureId: Image identifier.
     * @param pictureId HTTP Status:
     *                  200 OK: Everything worked as expected.
     *                  404 NOT FOUND: Car or picture don't exist.
     *                  500 INTERNAL SERVER ERROR: Server error.
     */
    @Operation(summary = "Delete Picture Car REST API", description = "Delete Picture Car REST API is used to remove particular picture from particular car in the database")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @SecurityRequirement(name = "Bear authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{elementId}/pictures/{pictureId}")
    public ResponseEntity<Response<CarDto>> deletePicture(
            @PathVariable(name = "elementId") Long elementId,
            @PathVariable(name = "pictureId") Long pictureId) {
        Response<CarDto> response = new Response<>();
        carService.deletePhoto(pictureId, elementId);
        response.setInfo("Picture successfully deleted");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Endpoint: GET /api/cars/{elementId}/pictures/{pictureId}
     * Description: Set the priority of a photo associated with a car.
     * Request Parameters:
     * elementId: Identifier of the car.
     *
     * @param elementId pictureId: Identifier of the photo.
     * @param pictureId HTTP Status:
     *                  200 OK: Photo priority successfully changed.
     *                  404 NOT FOUND: Car or picture doesn't exist.
     *                  406 NOT ACCEPTABLE: Picture doesn't belong to the specified element.
     *                  500 INTERNAL SERVER ERROR: Server error.
     */
    @Operation(summary = "Picture priority Car REST API", description = "Picture priority Car REST API is used to set priority status for particular picture in the database")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{elementId}/pictures/{pictureId}")
    public ResponseEntity<Response<CarDto>> setPhotoPriority(
            @PathVariable(name = "elementId") Long elementId,
            @PathVariable(name = "pictureId") Long pictureId) {
        Response<CarDto> response = new Response<>();
        carService.setPhotoPriority(pictureId, elementId);
        response.setInfo("Photo priority successfully changed");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
