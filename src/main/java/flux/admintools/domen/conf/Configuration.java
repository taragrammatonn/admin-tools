package flux.admintools.domen.conf;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Configuration {

    public static final String DB_HOST = "localhost";

    @Value("${spring.profiles.active}")
    String profile;

    @Value("${springbootwebfluxjjwt.password.encoder.secret}")
    String secret;

    @Value("${springbootwebfluxjjwt.password.encoder.iteration}")
    Integer iteration;

    @Value("${springbootwebfluxjjwt.password.encoder.keylength}")
    Integer keylength;

    @Value("${spring.r2dbc.username}")
    String dbUserName;

    @Value("${spring.r2dbc.password}")
    String dbPassword;


    public Boolean isDevMode() {
        return "dev".equals(profile);
    }

    public String indexRender() {
        return Boolean.TRUE.equals(isDevMode()) ? "dev_index" : "index";
    }
}
