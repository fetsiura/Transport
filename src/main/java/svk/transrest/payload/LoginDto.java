package svk.transrest.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Purpose:
 * The LoginDto class represents the data transfer object (DTO) used for encapsulating login credentials during the authentication process.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @NotNull(message = "Username or email must not be null")
    private String usernameOrEmail;
    @NotNull(message = "Password must not be null")
    private String password;
}
