package docvel.functionalDiagnostics.model.additionalEntities;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class Department implements Serializable {

    private long deptId;
    private String deptName;
    private Map<Integer, String> exams;
}
