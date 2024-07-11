package docvel.registry.exceptionsHandling;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public class ErrorContent {

    private Map<String, Object> error;

    public ErrorContent(Map<String, Object> error){
        this.error = error;
    }
}
