package nova.novahomepage.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nova.novahomepage.controller.dto.LoginDto;
import nova.novahomepage.controller.dto.RequestSignUp;
import nova.novahomepage.controller.dto.ResponseLogin;
import nova.novahomepage.controller.dto.UserDto;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@Api(tags = "유저 API를 제공하는 Controller")
public class UsersController {

    private final UsersService usersService;
    private final PreUsersService preUsersService;

    private final AuthService authService;

    @ApiOperation(value = "회원가입을 신청한다. -> 승인을 기다리는 유저 DB로 간다.")
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

    @ApiOperation(value = "관리자가 회원가입을 승인한다, number=학번")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/users/{number}")
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

    @ApiOperation(value = "로그인 한다.")
    @PostMapping("/login")
    public ResponseEntity<ResponseLogin> login(@Valid @RequestBody LoginDto loginDto) {
        log.info("========login start========");
        ResponseLogin token = authService.authenticate(loginDto.getStudentNumber(), loginDto.getPassword());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, token.getAccessToken());
        httpHeaders.add("USER", authentication.getAuthorities().toString());

        return new ResponseEntity<>(token, httpHeaders, HttpStatus.OK);
    }

    @ApiOperation(value = "회원가입을 거절한다, number=학번")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/preusers/{number}")
    public ResponseEntity deletePreUser(@PathVariable(name = "number") String preStudentNumber) {
        preUsersService.delete(preStudentNumber);
        return ResponseEntity.ok().body(null);
    }

    @ApiOperation(value = "유저를 삭제한다, number=학번")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/users/{number}")
    public ResponseEntity deleteUser(@PathVariable(name = "number") String studentNumber) {
        usersService.delete(studentNumber);
        return ResponseEntity.ok().body(null);
    }

    @ApiOperation(value = "모든 유저 정보를 본다.")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/all-users")
    public ResponseEntity<List<UserDto>> allUsers() {
        List<Users> users = usersService.allUsers();
        ModelMapper mapper = new ModelMapper();
        List<UserDto> collect = users.stream().
                map(user -> mapper.map(user, UserDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(collect);
    }

    @ApiOperation(value = "모든 회원가입 승인 요청을 본다.")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/all-preusers")
    public ResponseEntity<List<UserDto>> allPreUsers() {
        List<PreUsers> users = preUsersService.allPreUsers();
        ModelMapper mapper = new ModelMapper();
        List<UserDto> collect = users.stream().
                map(user -> mapper.map(user, UserDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(collect);
    }


    @ApiOperation(value = "해당 유저의 학번과 주민번호가 맞는지 확인한다. 맞으면 res에 true반환")
    @GetMapping("/password")
    public ResponseEntity<UserDto> isUser(@Valid @RequestBody UserDto userDto) {
        Users user = usersService.findUser(userDto.getStudentNumber());
        Boolean result = usersService.isUser(user, userDto.getSsn());
        userDto.setRes(result);

        return ResponseEntity.ok().body(userDto);
    }

    @ApiOperation(value = "비밀번호를 변경한다.")
    @PutMapping("/password")
    public ResponseEntity changePassword(@Valid @RequestBody LoginDto loginDto) {
        usersService.changePassword(loginDto.getStudentNumber(), loginDto.getPassword());
        return ResponseEntity.ok().body(null);
    }
}