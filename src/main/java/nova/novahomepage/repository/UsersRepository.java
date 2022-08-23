package nova.novahomepage.repository;

import nova.novahomepage.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByStudentNumber(String studentNumber);

}
