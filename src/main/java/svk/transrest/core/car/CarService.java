package svk.transrest.core.car;

import svk.transrest.payload.CarDto;
import svk.transrest.payload.response.Response;

public interface CarService {
    Response<CarDto> getCars(int page, int quantity, String sortBy, String sortDir);

    Response<CarDto> getCarById(Long id);

    Response<CarDto> createCar(CarDto carDto);

    void deleteCarById(Long id);

    Response<CarDto> updateCar(CarDto carDto, Long id);

    void deletePhoto(Long pictureId, Long carId);

    void setPhotoPriority(Long pictureId, Long carId);
}
