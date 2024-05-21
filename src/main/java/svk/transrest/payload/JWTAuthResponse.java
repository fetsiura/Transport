package svk.transrest.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Purpose:
 * The JWTAuthResponse class encapsulates the response containing a JWT access token, which is typically provided upon successful authentication, enabling secure access to protected resources.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthResponse {

    private String accessToken;
    private String tokenType = "Bearer";
}
