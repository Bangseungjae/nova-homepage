package nova.novahomepage.repository;

import nova.novahomepage.domain.entity.BusinessCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessCardRepository extends JpaRepository<BusinessCard, Long> {
}
