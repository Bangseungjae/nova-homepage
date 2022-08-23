package nova.novahomepage.service;

import lombok.RequiredArgsConstructor;
import nova.novahomepage.domain.entity.PreUsers;
import nova.novahomepage.repository.PreUsersRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreUsersService {
    private final PreUsersRepository preUsersRepository;

    public void save(PreUsers preUsers) {
        preUsersRepository.save(preUsers);
    }

    public PreUsers findUser(String number) {
        PreUsers preUsers = preUsersRepository.findByStudentNumber(number)
                .orElseThrow(() -> new IllegalArgumentException());
        return preUsers;
    }

}
