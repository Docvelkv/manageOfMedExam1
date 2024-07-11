package docvel.registrationOffice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "list_exam")
    private List<String> listExam;

    public Department(String departmentName, String... listExam) {
        this.departmentName = departmentName;
        this.listExam = List.of(listExam);
    }
}
