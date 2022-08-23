package nova.novahomepage.security.role;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class RoleHierarchyServiceImpl implements RoleHierarchyService {


    @Override
    public String findAllHierarchy() {
        return "ROLE_ADMIN > ROLE_USER\nROLE_USER > ROLE_READ";
    }
}
