package docvel.functionalDiagnostics.repository;

import docvel.functionalDiagnostics.model.report.MedicalReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<MedicalReport, Long> {
}
