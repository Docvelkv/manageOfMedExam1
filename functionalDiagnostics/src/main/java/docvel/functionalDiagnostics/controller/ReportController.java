package docvel.functionalDiagnostics.controller;

import docvel.functionalDiagnostics.model.entitys.Department;
import docvel.functionalDiagnostics.model.entitys.Patient;
import docvel.functionalDiagnostics.model.examinations.ecg.ECG;
import docvel.functionalDiagnostics.model.report.MedicalReport;
import docvel.functionalDiagnostics.service.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "deptFD")
public class CurrentDeptController {

    private final ExamService service;

    //region Patient
    @GetMapping(value = "pats")
    public ResponseEntity<List<Patient>> getAllPatient(){
        return ResponseEntity.ok().body(service.getAllPats());
    }

    @GetMapping(value = "patById/{patId}")
    public ResponseEntity<Patient> getPatById(@PathVariable long patId){
        return ResponseEntity.ok().body(service.getPatById(patId));
    }

    @GetMapping(value = "patByName/{patName}")
    public ResponseEntity<List<Patient>> getPatByName(@PathVariable String patName){
        return ResponseEntity.ok().body(service.getPatByName(patName));
    }
    //endregion

    //region Department
    @GetMapping(value = "depts")
    public ResponseEntity<List<Department>> getAllDepts(){
        return ResponseEntity.ok().body(service.getAllDepts());
    }

    @GetMapping(value = "deptById/{deptId}")
    public ResponseEntity<Department> getDeptsById(@PathVariable long deptId){
        return ResponseEntity.ok(service.getDeptById(deptId));
    }
    //endregion

    @PostMapping(value = "createNewECG/{refId}")
    public ResponseEntity<MedicalReport> createNewECGReport(@PathVariable long refId,
                                                            @RequestBody ECG exam){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createNewECGReport(refId, exam));
    }

    @PutMapping(value = "without_exam")
    public void closingRefWithoutExam(){
        service.closingRefWithoutExam();
    }
}
