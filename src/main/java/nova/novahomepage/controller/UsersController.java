package nova.novahomepage.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nova.novahomepage.controller.dto.LoginDto;
import nova.novahomepage.controller.dto.RequestSignUp;
import nova.novahomepage.controller.dto.ResponseLogin;
import nova.novahomepage.domain.Role;
import nova.novahomepage.domain.entity.Authority;
import nova.novahomepage.domain.entity.BusinessCard;
import nova.novahomepage.domain.entity.PreUsers;
import nova.novahomepage.domain.entity.Users;
import nova.novahomepage.repository.PreUsersRepository;
import nova.novahomepage.security.filter.JwtFilter;
import nova.novahomepage.service.PreUsersService;
import nova.novahomepage.service.UsersService;
import nova.novahomepage.service.security.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Controller
public class UsersController {

    private final UsersService usersService;
    private final PreUsersRepository preUsersRepository;
    private final PreUsersService preUsersService;

    private final AuthService authService;

    @PostMapping("/users")
    public ResponseEntity<RequestSignUp> signupRequest(@RequestBody @Valid RequestSignUp requestUser) {
        if (requestUser == null) {
            throw new IllegalStateException("유저 정보를 입력해주세요.");
        }

        ModelMapper mapper = new ModelMapper();
        PreUsers preUser = mapper.map(requestUser, PreUsers.class);
        preUsersService.save(preUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(requestUser);
    }

//    @Secured(Role.USER)
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PostMapping("admin/users/{number}")
    public ResponseEntity signup(@PathVariable(name = "number") String studentNumber) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authorities : {}", authentication.getAuthorities());
        PreUsers user = preUsersService.findUser(studentNumber);
        ModelMapper mapper = new ModelMapper();
        Set<Authority> authorities = new HashSet<>();
        authorities.add(Authority.builder().name(Role.USER).build());
        Users saveUser = Users.builder()
                .studentNumber(user.getStudentNumber())
                .name(user.getName())
                .password(user.getPassword())
                .authority(authorities)
                .ssn(user.getSsn())
                .businessCard(new BusinessCard())
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
}
