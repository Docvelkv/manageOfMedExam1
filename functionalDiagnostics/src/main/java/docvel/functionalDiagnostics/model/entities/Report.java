package docvel.functionalDiagnostics.model.entities;

import docvel.functionalDiagnostics.model.examinations.FunctionalDiagnosticsExam;
import docvel.functionalDiagnostics.tools.DateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "ref")
    private Referral ref;

    @NotNull
    @Column(name = "date_of_exam")
    private LocalDate dateOfExam;

    @NotNull
    @Column(name = "patient")
    private Patient patient;

    @Column(name = "examination")
    private FunctionalDiagnosticsExam exam;

    @Override
    public String toString(){
        @SuppressWarnings("StringBufferReplaceableByString")
        StringBuilder sb = new StringBuilder();

        sb.append("Заключение №: ").append(this.getId())
                .append("\n");
        sb.append("Обследование: ").append(this.getExam().getNameOfExam())
                .append("\n");
        sb.append("Дата проведения: ").append(this.dateOfExam)
                .append("\n");
        sb.append("ФИО пациента: ")
                .append(this.getPatient().getLastName()).append(" ")
                .append(this.getPatient().getFirstName()).append(" ")
                .append(this.getPatient().getSurname().trim())
                .append("\n");
        sb.append("Возраст пациента: ").append(DateTime
                .getAgeInUnits(DateTime
                        .getFullAge(this.getPatient().getDateOfBirth())))
                .append("\tпол: ").append(this.getPatient().getGender())
                .append("\n");
        sb.append(this.getExam())
                .append("\n");
        return sb.toString();
    }
}