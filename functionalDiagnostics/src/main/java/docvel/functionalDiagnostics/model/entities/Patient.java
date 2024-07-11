package docvel.functionalDiagnostics.model.additionalEntities;

import docvel.functionalDiagnostics.tools.DateTime;
import lombok.Data;

import java.io.Serializable;

@Data
public class Patient implements Serializable {

    private long patId;
    private String snils;
    private String firstName;
    private String surname;
    private String lastName;
    private String gender;
    private String dateOfBirth;
    private String fullAge = DateTime.getAgeInUnits(DateTime.getFullAge(dateOfBirth));
}
