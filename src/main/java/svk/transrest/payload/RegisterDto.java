package svk.transrest.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Purpose:
 * The RegisterDto class represents a data transfer object (DTO) used for transferring registration-related information from the client to the server during user registration processes.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    @NotNull(message = "Username must not be null")
    private String username;
    @Email
    @NotNull(message = "Email must not be null")
    private String email;
    @NotNull(message = "Password must not be null")
    private String password;

}
