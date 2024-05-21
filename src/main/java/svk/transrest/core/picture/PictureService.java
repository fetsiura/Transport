package svk.transrest.core.picture;

import svk.transrest.payload.PictureDto;

import java.util.List;

public interface PictureService {
    void deletePictureById(Long pictureId);

    void deletePictures(List<Picture> pictures);

    void savePictures(Object type, List<PictureDto> pictureDtoList);

    Picture getPictureById(Long id);
}
