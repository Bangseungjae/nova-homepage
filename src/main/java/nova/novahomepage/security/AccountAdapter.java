package nova.novahomepage.security;

import lombok.extern.slf4j.Slf4j;
import nova.novahomepage.domain.entity.Authority;
import nova.novahomepage.domain.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AccountAdapter extends User {

    private Users users;

    public AccountAdapter(Users users) {
        super(users.getStudentNumber(), users.getPassword(), authorities(users.getAuthority()));
    }

    private static List<GrantedAuthority> authorities(List<Authority> authorities) {
        log.info("authority: {}", authorities);
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
    }
}
