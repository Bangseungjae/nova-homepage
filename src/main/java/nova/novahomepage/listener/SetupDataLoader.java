package nova.novahomepage.listener;

import lombok.RequiredArgsConstructor;
import nova.novahomepage.domain.Role;
import nova.novahomepage.domain.entity.Authority;
import nova.novahomepage.domain.entity.PreUsers;
import nova.novahomepage.domain.entity.Users;
import nova.novahomepage.repository.PreUsersRepository;
import nova.novahomepage.repository.UsersRepository;
import nova.novahomepage.service.UsersService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    private final UsersRepository usersRepository;
    private final UsersService usersService;
    private final PreUsersRepository preUsersRepository;
    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<Authority> authorities1 = new ArrayList<>();
        authorities1.add(new Authority(Role.USER));
        List<Authority> authorities2 = new ArrayList<>();
        authorities2.add(new Authority(Role.ADMIN));
        createUserIfNotFound("방승재", "2020039110", "sj991209", 991209, authorities1);
        createUserIfNotFound("김상수", "2929110110", "tset1234", 991209, authorities2);

        createPreUserIfNotFound("나미", "2022030311", "test1234", 001234);
    }

    private void createUserIfNotFound(String name, String studentNumber, String password, Integer ssn, List<Authority> authorities) {
        Optional<Users> usersOptional = usersRepository.findByStudentNumber(studentNumber);
        Users user = new Users();
        if (usersOptional.isEmpty()) {

            user = Users.builder()
                    .name(name)
                    .studentNumber(studentNumber)
                    .password(password)
                    .ssn(ssn)
                    .authority(authorities)
                    .build();
            usersService.signup(user);
        }
        return;
    }

    @Transactional
    public void createPreUserIfNotFound(String name, String studentNumber,  String password,Integer ssn) {
        Optional<PreUsers> preUsersOptional = preUsersRepository.findByStudentNumber(studentNumber);
        PreUsers preUsers = null;
        if (preUsersOptional.isEmpty()) {
            preUsers = PreUsers.builder()
                    .name(name)
                    .studentNumber(studentNumber)
                    .ssn(ssn)
                    .password(password)
                    .build();
            preUsersRepository.save(preUsers);
        }
    }
}
