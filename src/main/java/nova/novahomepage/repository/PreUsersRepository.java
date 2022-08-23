package nova.novahomepage.repository;

import nova.novahomepage.domain.entity.PreUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PreUsersRepository extends JpaRepository<PreUsers, Long> {
    Optional<PreUsers> findByStudentNumber(String number);

    void deleteByStudentNumber(String number);
}
