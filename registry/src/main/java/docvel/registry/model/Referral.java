package docvel.registry.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ref_for_exam")
public class RefForMedExam {

    /**
     * Id направления
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long refId;

    /**
     * Id пациента
     */
    @NotNull
    @Positive
    @Column(name = "patientId")
    private long patientId;

    /**
     * Id отделения, где проводится обследование
     */
    @NotNull
    @Positive
    @Column(name = "department_id_for_exam")
    private long deptIdForExam;

    /**
     * Id обследования
     */
    @NotNull
    @Positive
    @Column(name = "exam_id")
    private int examId;

    /**
     * Дата и время проведения обследования (строка в формате "дд.мм.гггг.чч.мм")
     */
    @Pattern(regexp = "(0[1-9]|[12][0-9]|3[0-1])" +
            "\\.(0[1-9]|1[0-2])" +
            "\\.((19|20)\\d{2})" +
            "\\.(0[1-9]|1[0-9]|2[0-4])" +
            "\\.([0-5][0-9]|60)",
            message = "Не соответствует формату 'дд.мм.гггг.чч.мм'")
    @Column(name = "appointment_date")
    private String appointmentDate;

    /**
     * Статус (Возможные значения: -1(не проведено), 0(в работе), 1(проведено))
     */
    @Max(value = 1, message = "Значение не может быть больше 1")
    @Min(value = -1, message = "Значение не может быть меньше -1")
    @Column(name = "status")
    private int status;

    /**
     * Конструктор
     * @param patientId id пациента
     * @param deptIdForExam id отделения
     * @param examId id обследования
     * @param appointmentDate дата и время проведения обследования в формате "дд.мм.гггг.чч.мм"
     */
    public RefForMedExam(long patientId, long deptIdForExam,
                         int examId, String appointmentDate) {
        this.patientId = patientId;
        this.deptIdForExam = deptIdForExam;
        this.examId = examId;
        this.appointmentDate = appointmentDate;
        this.status = 0;
    }
}
