package svk.transrest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import svk.transrest.authentification.AuthService;
import svk.transrest.exception.ValidationErrorHandler;
import svk.transrest.payload.JWTAuthResponse;
import svk.transrest.payload.LoginDto;
import svk.transrest.payload.RegisterDto;

@Tag(name = "CRUD REST APIs for User resource")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    /**
     * Endpoint: POST /api/auth/login
     * Description: Authenticate a user and return a JWT token for authentication.
     * Request Body:
     * usernameOrEmail: Username or email of the user.
     * password: Password of the user.
     *
     * @param loginDto Response Body:
     *                 accessToken: JWT token for authentication.
     *                 HTTP Status:
     *                 200 OK: Authentication successful, JWT token returned.
     *                 400 BAD_REQUEST : Uncorrected user data.
     *                 401 UNAUTHORIZED: Authentication failed due to invalid credentials.
     */
    @Operation(summary = "Login REST API", description = "Login REST API is used for logging into database")
    @ApiResponse(responseCode = "200", description = "Http Status 200 SUCCESS")
    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult) {
        ResponseEntity<Object> errorResponse = ValidationErrorHandler.handleValidationErrors(bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }

        String token = authService.login(loginDto);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    /**
     * Endpoint: POST /api/auth/register
     * Description: Register a new user.
     * Request Body:
     * username: Username of the user.
     * email: Email of the user(must be unique).
     * password: Password of the user.
     *
     * @param registerDto HTTP Status:
     *                    201 OK: User registered successfully.
     *                    400 BAD_REQUEST : Uncorrected user data.
     *                    409 CONFLICT: Authentication failed, user email already exists.
     */
    @Operation(summary = "Register REST API", description = "Register REST API is used for register user into database")
    @ApiResponse(responseCode = "201", description = "Http Status 201 CREATED")
    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterDto registerDto, BindingResult bindingResult) {
        ResponseEntity<Object> errorResponse = ValidationErrorHandler.handleValidationErrors(bindingResult);
        if (errorResponse != null) {
            return errorResponse;
        }
        return ResponseEntity.ok(authService.register(registerDto));
    }

}
