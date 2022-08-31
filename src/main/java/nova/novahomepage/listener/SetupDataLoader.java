package nova.novahomepage.listener;

import lombok.RequiredArgsConstructor;
import nova.novahomepage.domain.Role;
import nova.novahomepage.domain.entity.*;
import nova.novahomepage.repository.BoardRepository;
import nova.novahomepage.repository.PreUsersRepository;
import nova.novahomepage.repository.UsersRepository;
import nova.novahomepage.service.BoardService;
import nova.novahomepage.service.UsersService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    private final UsersRepository usersRepository;
    private final UsersService usersService;
    private final PreUsersRepository preUsersRepository;
    private final BoardService boardService;
    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Set<Authority> authorities1 = new HashSet<>();
        authorities1.add(new Authority(Role.USER));
        Set<Authority> authorities2 = new HashSet<>();
        authorities2.add(new Authority(Role.ADMIN));
//        createUserIfNotFound("방승재", "2020039110", "sj991209", 991209, authorities1);
//        createUserIfNotFound("관리자", "1111111111", "nova1234", 900000, authorities2);

//        createPreUserIfNotFound("나미", "2022030311", "test1234", 001234);
//        createPreUserIfNotFound("우솝", "2051090112", "test1234", 001234);
//        createPreUserIfNotFound("루피", "1231231232", "test1234", 001234);

//        Users users = usersRepository.findByStudentNumber("2020039110").get();

//        for (int i = 0; i < 51; i++) {
//            createBoard("type", "title", "내용물", i, users);
//        }
    }


    private void createUserIfNotFound(String name, String studentNumber, String password, Integer ssn, Set<Authority> authorities) {
        Optional<Users> usersOptional = usersRepository.findByStudentNumber(studentNumber);
        Users user = null;
        BusinessCard businessCard = BusinessCard.builder()
                .name(name)
                .build();
        if (usersOptional.isEmpty()) {

            user = Users.builder()
                    .name(name)
                    .studentNumber(studentNumber)
                    .password(password)
                    .ssn(ssn)
                    .authority(authorities)
                    .businessCard(businessCard)
                    .build();
            usersService.signup(user);
        }
        return;
    }

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

    private void createBoard(String typeName, String title, String content, Integer good, Users users) {

        Optional<Board> optional = boardRepository.findById(0L);
        if (optional.isEmpty()) {
            Board board = Board.builder()
                    .users(users)
                    .typeName(typeName)
                    .title(title)
                    .content(content)
                    .good(good)
                    .build();
            boardService.makeBoard(board);
        }
    }
}
