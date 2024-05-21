package svk.transrest.core.car;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import svk.transrest.core.picture.Picture;
import svk.transrest.core.picture.PictureServiceImpl;
import svk.transrest.exception.PhotoNotBelongException;
import svk.transrest.exception.ResourceNotFoundException;
import svk.transrest.payload.CarDto;
import svk.transrest.payload.PictureDto;
import svk.transrest.payload.response.Response;

import java.util.List;

import static svk.transrest.utils.ModelMappers.mapFromCarDtoToCarWithoutId;

/**
 * Purpose:
 * The CarServiceImpl class provides service methods for managing car entities, including CRUD operations and handling related pictures.
 */
@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    @Qualifier("modelMapperWithoutPicturesAndId")
    private final ModelMapper modelMapperWithoutPicturesAndId;
    private final ModelMapper modelMapper;
    private final CarRepository carRepository;
    private final PictureServiceImpl pictureService;

    @Transactional
    @Override
    public Response<CarDto> getCars(int page, int quantity, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(page, quantity, sort);

        Page<Car> cars = carRepository.findAll(pageable);

        List<CarDto> content = cars.getContent()
                .stream()
                .map(car -> modelMapper.map(car, CarDto.class)).toList();

        Response<CarDto> response = new Response<>();
        response.setContent(content);
        response.setPage(cars.getNumber());
        response.setTotalPages(cars.getTotalPages());
        response.setTotalElements(cars.getTotalElements());
        response.setLast(cars.isLast());
        return response;
    }

    @Transactional
    @Override
    public Response<CarDto> getCarById(Long id) {
        Car getCarById = carRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Car", "id", id));
        CarDto carDto = modelMapper.map(getCarById, CarDto.class);

        Response<CarDto> response = new Response<>();
        response.setContent(List.of(carDto));
        return response;
    }

    @Transactional
    @Override
    public Response<CarDto> createCar(CarDto carDto) {

        //custom mapping for avoid double pictures saving
        Car newCar = mapFromCarDtoToCarWithoutId(carDto);
        carRepository.save(newCar);

        //separate picture saving procedure, first picture gets priority status
        List<PictureDto> attachedPictures = carDto.getPictures();
        if (attachedPictures != null && !attachedPictures.isEmpty()) {
            attachedPictures.get(0).setPriority(true);
            attachedPictures.subList(1, attachedPictures.size()).forEach(p -> p.setPriority(false));
            pictureService.savePictures(newCar, attachedPictures);
        }

        CarDto newCarDto = modelMapper.map(newCar, CarDto.class);
        Response<CarDto> response = new Response<>();
        response.setContent(List.of(newCarDto));
        return response;
    }

    @Transactional
    @Override
    public void deleteCarById(Long id) {
        Car byId = carRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Car", "id", id));
        pictureService.deletePictures(byId.getPictures());
        carRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Response<CarDto> updateCar(CarDto carDto, Long id) {
        Car car = carRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Car", "id", id));
        //mapping without picture for avoid double picture saving
        modelMapperWithoutPicturesAndId.map(carDto, car);

        List<PictureDto> attachedPictures = carDto.getPictures();
        if (attachedPictures != null && !attachedPictures.isEmpty()) {
            attachedPictures.forEach(p -> p.setPriority(false));
            pictureService.savePictures(car, attachedPictures);
        }

        Car updatedCar = carRepository.saveAndFlush(car);
        CarDto updatedCarDto = modelMapper.map(updatedCar, CarDto.class);
        Response<CarDto> response = new Response<>();
        response.setContent(List.of(updatedCarDto));
        return response;
    }

    @Transactional
    @Override
    public void deletePhoto(Long pictureId, Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new ResourceNotFoundException("Car", "id", carId));
        pictureService.getPictureById(pictureId);
        boolean photoBelongToElement = ifPhotoBelongToCar(pictureId, car);
        if (!photoBelongToElement) {
            throw new PhotoNotBelongException(pictureId, carId);
        } else {
            pictureService.deletePictureById(pictureId);

            //update list after removing
            List<Picture> pictures = car.getPictures();
            pictures.removeIf(p -> p.getId().equals(pictureId));

            pictureService.replacementPicturePriorityAfterRemoving(pictures);
        }
    }

    @Transactional
    @Override
    public void setPhotoPriority(Long pictureId, Long carId) {
        Car car = carRepository.findById(carId).orElseThrow(() -> new ResourceNotFoundException("Car", "id", carId));
        boolean photoBelongToElement = ifPhotoBelongToCar(pictureId, car);

        if (!photoBelongToElement) {
            throw new PhotoNotBelongException(pictureId, carId);
        } else {
            List<Picture> pictures = car.getPictures();
            pictures.forEach(p -> p.setPriority(p.getId().equals(pictureId)));
            carRepository.save(car);
        }
    }

    private boolean ifPhotoBelongToCar(Long pictureId, Car car) {
        return car.getPictures().stream()
                .map(Picture::getId)
                .anyMatch(id -> id.equals(pictureId));
    }


}
