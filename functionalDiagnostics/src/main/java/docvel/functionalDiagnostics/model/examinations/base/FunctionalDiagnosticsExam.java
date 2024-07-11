package docvel.functionalDiagnostics.model.examinations;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class FunctionalDiagnosticsExam implements Serializable {

    private String nameOfExam;

    public FunctionalDiagnosticsExam(String nameOfExam){
        this.nameOfExam = nameOfExam;
    }
}
