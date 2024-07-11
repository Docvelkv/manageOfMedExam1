package docvel.registry.repository;

import docvel.registry.model.RefForMedExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferralRepository extends JpaRepository<RefForMedExam, Long> {
}
