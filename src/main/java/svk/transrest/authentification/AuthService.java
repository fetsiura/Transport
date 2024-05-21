package svk.transrest.authentification;

import svk.transrest.payload.LoginDto;
import svk.transrest.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
