package svk.transrest.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Purpose:
 * The PictureDto class serves as a data transfer object (DTO) for representing picture-related information, particularly when transmitting data between different layers of the application or between the client and server components.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PictureDto {
    private Long id;
    @NotNull(message = "Filename must not be null")
    private String filename;
    private boolean priority;
}