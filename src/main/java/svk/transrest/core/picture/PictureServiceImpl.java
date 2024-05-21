package svk.transrest.core.picture;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import svk.transrest.core.car.Car;
import svk.transrest.exception.ResourceNotFoundException;
import svk.transrest.payload.PictureDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static svk.transrest.utils.ModelMappers.mapFromPictureDtoToPicture;

/**
 * Purpose:
 * The PictureServiceImpl class provides implementations for managing pictures associated with cars in the application.
 */
@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public void savePictures(Object type, List<PictureDto> pictureDtoList) {
        List<Picture> picturesForSaving = pictureDtoList.stream()
                .map(pictureDto -> {
                    Picture newPicture = mapFromPictureDtoToPicture(pictureDto);
                    if (type instanceof Car) {
                        newPicture.setCar((Car) type);
                    }
                    return newPicture;
                })
                .collect(Collectors.toList());

        pictureRepository.saveAll(picturesForSaving);
    }

    @Transactional
    @Override
    public Picture getPictureById(Long id) {
        return pictureRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Picture", "id", id));
    }

    @Transactional
    @Override
    public void deletePictureById(Long pictureId) {
        pictureRepository.deleteById(pictureId);
    }

    @Transactional
    public void deletePictures(List<Picture> pictures) {
        Optional.ofNullable(pictures)
                .filter(pics -> !pics.isEmpty())
                .ifPresent(pictureRepository::deleteAll);
    }

    @Transactional
    public void replacementPicturePriorityAfterRemoving(List<Picture> pictures) {
        if (pictures != null && !pictures.isEmpty()) {
            Picture picture = pictures.get(0);
            picture.setPriority(true);
            pictureRepository.save(picture);
        }
    }

}
