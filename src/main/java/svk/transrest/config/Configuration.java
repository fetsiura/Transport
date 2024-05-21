package svk.transrest.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import svk.transrest.core.car.Car;
import svk.transrest.payload.CarDto;

@org.springframework.context.annotation.Configuration
public class Configuration {

    /**
     * Purpose: Configures and provides a ModelMapper bean instance.
     * Configuration:
     * Enables skipping null properties during mapping.
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }

    /**
     * Purpose: Configures and provides a ModelMapper bean instance with a custom property mapping to exclude pictures during mapping from CarDto to Car.
     * Configuration:
     * Configures a property mapping to skip the pictures property during mapping.
     * Enables skipping null properties during mapping.
     * Returns:
     * A configured ModelMapper bean.
     */
    @Bean
    public ModelMapper modelMapperWithoutPicturesAndId() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<CarDto, Car>() {
            @Override
            protected void configure() {
                skip(destination.getPictures());
                skip(destination.getId());
            }
        });
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }


}
