package nova.novahomepage.security.init;

import nova.novahomepage.security.role.RoleHierarchyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

public class SecurityInitializer implements ApplicationRunner {

    @Autowired
    private RoleHierarchyService roleHierachyService;
    @Autowired
    private RoleHierarchyImpl roleHierarchy;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        roleHierarchy.setHierarchy(roleHierachyService.findAllHierarchy());
    }
}
