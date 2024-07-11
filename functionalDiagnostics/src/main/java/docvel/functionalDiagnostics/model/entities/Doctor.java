package docvel.functionalDiagnostics.model.doctors;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Pattern(regexp = "(([А-ЯЁа-яё]-?)\\s?){2,}")
    @Column(name = "full_name")
    private String fullName;

    @
    private String deptName;
}
