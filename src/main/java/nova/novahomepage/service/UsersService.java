package nova.novahomepage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nova.novahomepage.domain.Role;
import nova.novahomepage.domain.entity.Users;
import nova.novahomepage.repository.PreUsersRepository;
import nova.novahomepage.repository.UsersRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {
    private final UsersRepository usersRepository;
    private final PreUsersRepository preUsersRepository;
    private final PasswordEncoder passwordEncoder;

//    @RolesAllowed("[ROLE_ADMIN]")
    @Transactional
    public void signup(Users users) {

        if (users == null) {
            throw new IllegalArgumentException("유저 정보가 없습니다.");
        }
        Optional<Users> optionalUsers = usersRepository.findByStudentNumber(users.getStudentNumber());
        if (optionalUsers.isPresent()) {
            throw new IllegalArgumentException("이미 있는 유저입니다.");
        }
        preUsersRepository.deleteByStudentNumber(users.getStudentNumber());
        String encode = passwordEncoder.encode(users.getPassword());
        log.info("encoded Password:  " + encode);
        users.setEncodedPassword(encode);
        usersRepository.save(users);
    }
}
