package nova.novahomepage.repository;

import nova.novahomepage.domain.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
