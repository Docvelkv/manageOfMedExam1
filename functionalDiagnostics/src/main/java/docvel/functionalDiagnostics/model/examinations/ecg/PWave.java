package docvel.functionalDiagnostics.model.examinations.ecg;

import lombok.Getter;

@Getter
public enum DirectP {

    POS("положительный"),
    NEG("отрицательный"),
    TWO_PHASE("двухфазный"),
    NONE("отсутствует"),
    SAW("пилообразный");

    private final String description;

    DirectP(String description) {
        this.description = description;
    }
}
