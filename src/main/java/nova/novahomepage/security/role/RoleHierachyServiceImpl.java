package nova.novahomepage.security.role;

import org.springframework.stereotype.Service;

@Service
public class RoleHierachyServiceImpl implements RoleHierachyService{


    @Override
    public String findAllHierachy() {
        return "ROLE_ADMIN > ROLE_USER > ROLE_READ";
    }
}
