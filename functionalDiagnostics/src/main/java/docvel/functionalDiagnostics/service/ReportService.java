package docvel.functionalDiagnostics.service;

import docvel.functionalDiagnostics.model.entitys.Department;
import docvel.functionalDiagnostics.model.entitys.Patient;
import docvel.functionalDiagnostics.model.entitys.RefForMedicalExam;
import docvel.functionalDiagnostics.model.examinations.ecg.ECG;
import docvel.functionalDiagnostics.model.report.MedicalReport;
import docvel.functionalDiagnostics.providers.DepartmentsProvider;
import docvel.functionalDiagnostics.providers.PatientsProvider;
import docvel.functionalDiagnostics.providers.ReferralsProvider;
import docvel.functionalDiagnostics.repository.ExamRepository;
import docvel.functionalDiagnostics.tools.DateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository repository;
    private final PatientsProvider patProvider;
    private final ReferralsProvider refProvider;
    private final DepartmentsProvider deptProvider;

    //region Patient
    public List<Patient> getAllPats(){
        return patProvider.getAllPats();
    }

    public Patient getPatById(long patId){
        return patProvider.getPatById(patId);
    }

    public List<Patient> getPatByName(String patName){
        return patProvider.getPatByName(patName);
    }
    //endregion

    //region Department
    public List<Department> getAllDepts(){
        return deptProvider.getAllDepartment();
    }

    public Department getDeptById(long deptId){
        return deptProvider.getDeptById(deptId);
    }

    public long getCurDeptById(){
        return deptProvider.getCurrentDeptId();
    }

    public Map<Integer, String> getAllExamsOfDept(long deptId){
        return deptProvider.getAllExamsOfDept(deptId);
    }

    public String getExamById(long deptId, Integer examId){
        return deptProvider.getExamById(deptId, examId);
    }
    //endregion

    public MedicalReport createNewECGReport(long refId, ECG exam){
        RefForMedicalExam ref = refProvider.getRefById(refId).orElseThrow(
                () -> new NoSuchElementException("Направления с id %d не найдено."));
        Patient patient = patProvider.getPatById(ref.getPatientId());
        MedicalReport newReport = new MedicalReport(patient.getPatId(), exam);
        ref.setStatus(1);
        refProvider.updateRef(refId, 1);
        return repository.save(newReport);
    }

    public void closingRefWithoutExam(){
        List<RefForMedicalExam> list = refProvider.getRefByDeptAndByStatusOnCurDate(0).stream()
                .filter(ref -> DateTime.getDateTimeFromString(ref.getAppointmentDate())
                        .toLocalDate().isBefore(LocalDate.now()))
                .toList();
        for (RefForMedicalExam ref : list){
            refProvider.updateRef(ref.getRefId(), -1);
        }
    }

}
