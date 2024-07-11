package docvel.registry.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Gender {
    @JsonProperty("М")
    M("мужской"),
    @JsonProperty("Ж")
    F("женский");

    private final String description;
}
