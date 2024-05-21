package svk.transrest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot Transport REST APIs",
                description = "SpringBoot Transport App REST Apis documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "Jaro Fetsiura",
                        email = "fetsiura@gmail.com"
                )
        )
)
public class TransportApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransportApplication.class, args);
    }

}
