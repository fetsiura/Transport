package svk.transrest.utils;

import svk.transrest.core.car.Car;
import svk.transrest.core.picture.Picture;
import svk.transrest.payload.CarDto;
import svk.transrest.payload.PictureDto;

import java.util.Collections;

public class ModelMappers {

    public static Picture mapFromPictureDtoToPicture(PictureDto pictureDto) {
        Picture picture = new Picture();
        picture.setPriority(pictureDto.isPriority());
        picture.setFilename(pictureDto.getFilename());
        return picture;
    }

    public static PictureDto mapFromPictureToPictureDto(Picture picture) {
        PictureDto pictureDto = new PictureDto();
        pictureDto.setId(picture.getId());
        pictureDto.setPriority(picture.isPriority());
        pictureDto.setFilename(picture.getFilename());
        return pictureDto;
    }

    public static Car mapFromCarDtoToCarWithoutId(CarDto carDto) {
        Car car = new Car();
        car.setMake(carDto.getMake());
        car.setModel(carDto.getModel());
        car.setPrice(carDto.getPrice());
        car.setYearOfRegistration(carDto.getYearOfRegistration());
        car.setMileage(carDto.getMileage());
        car.setColor(carDto.getColor());
        car.setBodyType(carDto.getBodyType());
        car.setFuelType(carDto.getFuelType());
        return car;
    }

    public static CarDto mapFromCarToCarDtoWithoutPictures(Car car) {
        CarDto carDto = new CarDto();
        carDto.setId(car.getId());
        carDto.setMake(car.getMake());
        carDto.setModel(car.getModel());
        carDto.setPrice(car.getPrice());
        carDto.setYearOfRegistration(car.getYearOfRegistration());
        carDto.setMileage(car.getMileage());
        carDto.setColor(car.getColor());
        carDto.setBodyType(car.getBodyType());
        carDto.setFuelType(car.getFuelType());
        carDto.setPictures(Collections.emptyList());
        return carDto;
    }
}
