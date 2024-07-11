package docvel.functionalDiagnostics.model.additionalEntities;

import lombok.Data;

import java.io.Serializable;

@Data
public class Referral implements Serializable {

    private long refId;
    private long patientId;
    private long departmentIdForExam;
    private long examId;
    private String appointmentDate;
    private int status;
}
