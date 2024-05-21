package svk.transrest.authentification;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import svk.transrest.core.role.Role;
import svk.transrest.core.user.User;
import svk.transrest.core.user.UserRepository;
import svk.transrest.exception.TransAPIException;
import svk.transrest.payload.LoginDto;
import svk.transrest.payload.RegisterDto;
import svk.transrest.security.JwtTokenProvider;

import java.util.HashSet;
import java.util.Set;

/**
 * The AuthService class provides authentication and user registration functionalities for the application.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    /**
     * Purpose: Authenticate a user and generate a JWT token for authentication.
     * Parameters:
     * loginDto: A data transfer object containing the username or email and password of the user.
     *
     * @param loginDto Returns:
     *                 A JWT token for successful authentication.
     */
    @Override
    public String login(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String token = tokenProvider.generateToken(authenticate);
        return token;
    }

    /**
     * Purpose: Register a new user in the system.
     * Parameters:
     * registerDto: A data transfer object containing the username, email, and password of the user.
     *
     * @param registerDto Returns:
     *                    A success message indicating that the user has been registered successfully.
     */
    @Override
    public String register(RegisterDto registerDto) {
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new TransAPIException(HttpStatus.CONFLICT, "User email already exists.");
        }
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        user.setRoles(roles);
        userRepository.save(user);

        return "User registered successfully";
    }
}
