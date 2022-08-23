package nova.novahomepage.security.role;

import org.springframework.stereotype.Service;

@Service
public class RoleHierarchyServiceImpl implements RoleHierarchyService {


    @Override
    public String findAllHierarchy() {
        return "ROLE_ADMIN > ROLE_USER > ROLE_READ";
    }
}
