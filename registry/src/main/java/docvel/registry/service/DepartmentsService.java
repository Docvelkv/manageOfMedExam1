package docvel.registry.service;

import docvel.registry.exceptionsHandling.ResourceAlreadyExistsException;
import docvel.registry.exceptionsHandling.ResourceNotFoundException;
import docvel.registry.model.Department;
import docvel.registry.model.Patient;
import docvel.registry.model.RefForMedExam;
import docvel.registry.repository.DepartmentRepository;
import docvel.registry.repository.PatientsRepository;
import docvel.registry.repository.ReferralRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistryService {

    private final PatientsRepository patients;
    private final ReferralRepository referrals;
    private final DepartmentRepository departments;
    private final String noPatById = "Пациент с id %d не найден.";
    private final String noDeptById = "Отделение с id %d не найдено.";

    //region Patients
    public List<Patient> getAllPats(){
        List<Patient> list = patients.findAll();
        if(list.isEmpty()){
            throw new ResourceNotFoundException("Пациентов пока нет.");
        }
        return list;
    }

    public Patient getPatById(long patId){
        return patients.findById(patId).orElseThrow(
                () -> new ResourceNotFoundException(String
                        .format(noPatById, patId)));
    }

    public List<Patient> getPatsByName(String name){
        List<Patient> list = patients.findAll().stream()
                .filter(patient -> patient.getLastName().contains(name))
                .toList();
        if(list.isEmpty()){
            throw new ResourceNotFoundException(String
                    .format("Пациент с данными '%s' в фамилии не найден.", name));
        }
        return list;
    }

    public Patient createNewPat(Patient pat){
        if(patients.findAll().stream()
                .anyMatch(anyPatient -> anyPatient.equals(pat))){
            throw new ResourceAlreadyExistsException("Такой пациент уже существует.");
        }
        Patient newPat = new Patient(pat.getSnils(), pat.getFirstName(),
                pat.getSurname(), pat.getLastName(), pat.getGender(),
                pat.getDateOfBirth());
        return patients.save(newPat);
    }

    public void deletePatById(long patId){
        if(patients.findById(patId).isEmpty()){
            throw new ResourceNotFoundException(String
                    .format(noPatById, patId));
        }
        patients.deleteById(patId);
    }

    public Patient updatePat(long patId, Patient newPat){
        Patient pat = patients.findById(patId).orElseThrow(
                () -> new ResourceNotFoundException(String
                        .format(noPatById, patId)));
        pat.setSnils(newPat.getSnils());
        pat.setFirstName(newPat.getFirstName());
        pat.setSurname(newPat.getSurname());
        pat.setLastName(newPat.getLastName());
        pat.setGender(newPat.getGender());
        pat.setDateOfBirth(newPat.getDateOfBirth());
        pat.setAgeInUnits(Patient.getAgeInUnits(Patient.getFullAge(newPat.getDateOfBirth())));

        return patients.save(pat);
    }
    //endregion

    //region Referrals
    public List<RefForMedExam> getAllRef(){
        List<RefForMedExam> list = referrals.findAll();
        if(list.isEmpty()){
            throw new ResourceNotFoundException("Направлений пока не создано.");
        }
        return list;
    }

    public List<RefForMedExam> getAllRefByDeptAndByStatusOnCurDate(long deptId, int status){
        if(status < -1 || status > 1){
            throw new InvalidParameterException("Параметр status должен соответствовать: " +
                    "'-1' - обследование не проведено, '0' - в работе, '1' - проведено");
        }
        if(departments.findById(deptId).isEmpty()){
            throw new ResourceNotFoundException(String
                    .format(noDeptById, deptId));
        }
        List<RefForMedExam> list = referrals.findAll().stream()
                .filter(ref -> ref.getDepartmentIdForExam() == deptId)
                .filter(ref ->
                    ref.getAppointmentDate().toLocalDate().equals(LocalDate.now()))
                .filter(ref -> ref.getStatus() == status)
                .toList();
        if(list.isEmpty()){
            throw new ResourceNotFoundException(String
                    .format("На %s обследований не назначено.",
                            LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
        }
        return list;
    }

    public RefForMedExam createNewRef(RefForMedExam newRef){
        return referrals.save(newRef);
    }

    public void deleteRef(long refId){
        if(referrals.findById(refId).isEmpty()){
            throw new ResourceNotFoundException(String
                    .format("Направления с id %d не найдено.", refId));
        }
        referrals.deleteById(refId);
    }
    //endregion

    // region Departments
    public List<Department> getAllDepts(){
        List<Department> list = departments.findAll();
        if(list.isEmpty()){
            throw new ResourceNotFoundException("Отделений пока не существует.");
        }
        return list;
    }

    public Department getDeptById(long deptId){
        return departments.findById(deptId).orElseThrow(
                () -> new ResourceNotFoundException(String
                        .format(noDeptById, deptId)));
    }

    public String getListAllExamsOfDept(long deptId){
        if(departments.findById(deptId).isEmpty()){
            throw new ResourceNotFoundException(String
                    .format(noDeptById, deptId));
        }
        List<String> exams = departments.findById(deptId).get().getListExam();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < exams.size(); i++){
            sb.append(i++).append(". ").append(exams.get(i)).append("\n");
        }
        return sb.toString();
    }

    public Department createNewDept(Department dept){
        return departments.save(dept);
    }

    public Department updateDept(long deptId, Department newDept){
        Department dept = departments.findById(deptId).orElseThrow(
                () -> new ResourceNotFoundException(String
                        .format(noDeptById, deptId)));
        dept.setDepartmentName(newDept.getDepartmentName());
        dept.setListExam(newDept.getListExam());
        return departments.save(dept);
    }

    public void deleteDeptById(long deptId){
        if(departments.findById(deptId).isEmpty()){
            throw new ResourceNotFoundException(String
                    .format(noDeptById, deptId));
        }
        departments.deleteById(deptId);
    }
    //endregion

}
