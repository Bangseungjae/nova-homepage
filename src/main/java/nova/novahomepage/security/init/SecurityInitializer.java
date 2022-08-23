package nova.novahomepage.security.init;

import nova.novahomepage.security.role.RoleHierachyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

public class SecurityInitializer implements ApplicationRunner {

    @Autowired
    private RoleHierachyService roleHierachyService;
    @Autowired
    private RoleHierarchyImpl roleHierarchy;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        roleHierarchy.setHierarchy(roleHierachyService.findAllHierachy());
    }
}
