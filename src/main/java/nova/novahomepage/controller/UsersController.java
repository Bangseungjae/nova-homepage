package nova.novahomepage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nova.novahomepage.controller.dto.LoginDto;
import nova.novahomepage.controller.dto.RequestSignUp;
import nova.novahomepage.controller.dto.ResponseLogin;
import nova.novahomepage.controller.dto.ResponseUser;
import nova.novahomepage.domain.Role;
import nova.novahomepage.domain.entity.Authority;
import nova.novahomepage.domain.entity.BusinessCard;
import nova.novahomepage.domain.entity.PreUsers;
import nova.novahomepage.domain.entity.Users;
import nova.novahomepage.security.filter.JwtFilter;
import nova.novahomepage.service.PreUsersService;
import nova.novahomepage.service.UsersService;
import nova.novahomepage.service.security.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Controller
public class UsersController {

    private final UsersService usersService;
    private final PreUsersService preUsersService;

    private final AuthService authService;

    @PostMapping("/preusers")
    public ResponseEntity<RequestSignUp> signupRequest(@RequestBody @Valid RequestSignUp requestUser) {
        if (requestUser == null) {
            throw new IllegalStateException("유저 정보를 입력해주세요.");
        }

        ModelMapper mapper = new ModelMapper();
        PreUsers preUser = mapper.map(requestUser, PreUsers.class);
        preUsersService.save(preUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(requestUser);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("users/{number}")
    public ResponseEntity signup(@PathVariable(name = "number") String studentNumber) {
        PreUsers user = preUsersService.findUser(studentNumber);
        Set<Authority> authorities = new HashSet<>();
        authorities.add(Authority.builder().name(Role.USER).build());
        BusinessCard businessCard = BusinessCard.builder()
                .name(user.getName())
                .build();
        Users saveUser = Users.builder()
                .studentNumber(user.getStudentNumber())
                .name(user.getName())
                .password(user.getPassword())
                .authority(authorities)
                .ssn(user.getSsn())
                .businessCard(businessCard)
                .build();

        usersService.signup(saveUser);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseLogin> login(@Valid @RequestBody LoginDto loginDto) {
        log.info("========login start========");
        ResponseLogin token = authService.authenticate(loginDto.getStudentNumber(), loginDto.getPassword());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, token.getAccessToken());

        return new ResponseEntity<>(token, httpHeaders, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/preusers/{number}")
    public ResponseEntity deletePreUser(@PathVariable(name = "number") String preStudentNumber) {
        preUsersService.delete(preStudentNumber);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/users/{number}")
    public ResponseEntity deleteUser(@PathVariable(name = "number") String studentNumber) {
        usersService.delete(studentNumber);
        return ResponseEntity.ok().body(null);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/all-users")
    public ResponseEntity<List<ResponseUser>> allUsers() {
        List<Users> users = usersService.allUsers();
        ModelMapper mapper = new ModelMapper();
        List<ResponseUser> collect = users.stream().
                map(user -> mapper.map(user, ResponseUser.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(collect);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/all-preusers")
    public ResponseEntity<List<ResponseUser>> allPreUsers() {
        List<PreUsers> users = preUsersService.allPreUsers();
        ModelMapper mapper = new ModelMapper();
        List<ResponseUser> collect = users.stream().
                map(user -> mapper.map(user, ResponseUser.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(collect);
    }
}
