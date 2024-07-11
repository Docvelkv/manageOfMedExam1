package docvel.functionalDiagnostics.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Gender {

    @JsonProperty("М")
    M("мужской"),
    @JsonProperty("Ж")
    F("женский");

    private final String description;
}
