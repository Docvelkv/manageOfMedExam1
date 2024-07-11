package docvel.functionalDiagnostics.exceptionsHandling;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class ErrorList {

    private final List<ErrorContent> errors;
}