package nova.novahomepage.repository;

import nova.novahomepage.domain.entity.Chatting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingRepository extends JpaRepository<Chatting, Long> {
}
