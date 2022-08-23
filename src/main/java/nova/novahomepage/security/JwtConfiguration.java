package nova.novahomepage.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {
    @Value("${jwt.secret}")
    private String accessTokenSecret;
    @Value("${jwt.access-token-validity-in-seconds}")
    private Long accessTokenValidityInSeconds;

    // 액세스 토큰 발급용, 리프레시 토큰 발급용은 각각 별도의 키와 유효기간을 갖는다.
    @Bean //(name = "tokenProvider")
    public TokenProvider tokenProvider() {
        return new TokenProvider(accessTokenSecret, accessTokenValidityInSeconds);
    }
}
