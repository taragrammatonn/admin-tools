package flux.admintools.domen.conf;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Configuration {

    @Value("${spring.profiles.active}")
    String profile;

    @Value("${springbootwebfluxjjwt.password.encoder.secret}")
    private String secret;

    @Value("${springbootwebfluxjjwt.password.encoder.iteration}")
    private Integer iteration;

    @Value("${springbootwebfluxjjwt.password.encoder.keylength}")
    private Integer keylength;


    public Boolean isDevMode() {
        return "dev".equals(profile);
    }

    public String indexRender() {
        return Boolean.TRUE.equals(isDevMode()) ? "dev_index" : "index";
    }
}
