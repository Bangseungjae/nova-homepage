package nova.novahomepage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nova.novahomepage.domain.entity.Users;
import nova.novahomepage.repository.PreUsersRepository;
import nova.novahomepage.repository.dsl.UsersQueryDsl;
import nova.novahomepage.repository.UsersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UsersService {
    private final UsersRepository usersRepository;
    private final PreUsersRepository preUsersRepository;
    private final UsersQueryDsl usersQueryDsl;
    private final PasswordEncoder passwordEncoder;

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

    public void delete(String studentNumber) {
        Users users = usersRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        usersRepository.delete(users);
    }

    @Transactional(readOnly = true)
    public List<Users> allUsers() {
        return usersRepository.findAll();
    }

    public Users findUser(String studentNumber) {
        return usersRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
    }

    public Boolean isUser(Users users, Integer ssn) {
        if (users.getSsn().equals(ssn)) {
            return true;
        }
        return false;
    }

    public void changePassword(String studentNumber, String password) {
        String encode = passwordEncoder.encode(password);
        usersQueryDsl.changePassword(studentNumber, encode);
    }
}
