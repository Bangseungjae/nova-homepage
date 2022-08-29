package nova.novahomepage.service.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nova.novahomepage.controller.dto.ResponseLogin;
import nova.novahomepage.domain.entity.Users;
import nova.novahomepage.repository.UsersQueryDsl;
import nova.novahomepage.repository.UsersRepository;
import nova.novahomepage.security.TokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsersQueryDsl usersQueryDsl;

    //username 과 패스워드로 사용자 인증하여 엑세스 토큰을 반환한다.
    @Transactional(readOnly = true)
    public ResponseLogin authenticate(String studentNumber, String password) {
        Users user = usersRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다 : " + studentNumber));

        matchPassword(password, user);

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(studentNumber, password);

        // authenticationToken 객체를 통해 Authentication 객체 생성
        // 이 과정에서 CustomUserDetailsService 에서 우리가 재정의한 loadUserByUsername 메서드 호출
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 인증정보를 기준으로 jwt access 토큰 생성
        String token = tokenProvider.createToken(authentication);
        return ResponseLogin.builder()
                .accessToken(token)
                .studentNumber(studentNumber)
                .build();
    }

    private void matchPassword(String password, Users user) {
        log.info("password : {}", user.getPassword());
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        log.info("======match start======");
        if (!matches) {
            log.info("패스워드가 일치하지 않습니다.");
            throw new IllegalStateException("패스워드가 일치하지 않습니다.");
        }
    }

}
