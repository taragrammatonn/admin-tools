package flux.admintools.configuration;

import flux.admintools.domen.conf.Configuration;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;

@Component
public class PBKDF2Encoder implements PasswordEncoder {

    private final Configuration configuration;

    public PBKDF2Encoder(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    @SneakyThrows
    public String encode(CharSequence cs) {
        byte[] result = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512")
                .generateSecret(new PBEKeySpec(
                        cs.toString().toCharArray(),
                        configuration.getSecret().getBytes(),
                        configuration.getIteration(),
                        configuration.getKeylength())
                )
                .getEncoded();
        return Base64.getEncoder().encodeToString(result);
    }

    @Override
    public boolean matches(CharSequence cs, String encodedPassword) {
        return encode(cs).equals(encodedPassword);
    }
}
