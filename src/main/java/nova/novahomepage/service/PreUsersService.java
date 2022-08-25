package nova.novahomepage.service;

import lombok.RequiredArgsConstructor;
import nova.novahomepage.domain.entity.PreUsers;
import nova.novahomepage.repository.PreUsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PreUsersService {
    private final PreUsersRepository preUsersRepository;


    public void save(PreUsers preUsers) {
        preUsersRepository.save(preUsers);
    }

    @Transactional(readOnly = true)
    public PreUsers findUser(String number) {
        PreUsers preUsers = preUsersRepository.findByStudentNumber(number)
                .orElseThrow(() -> new IllegalArgumentException());
        return preUsers;
    }

    public void delete(String preStudentNumber) {
        PreUsers preUsers = preUsersRepository.findByStudentNumber(preStudentNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생이 없습니다."));
        preUsersRepository.delete(preUsers);
    }

    @Transactional(readOnly = true)
    public List<PreUsers> allPreUsers() {
        return preUsersRepository.findAll();
    }
}
