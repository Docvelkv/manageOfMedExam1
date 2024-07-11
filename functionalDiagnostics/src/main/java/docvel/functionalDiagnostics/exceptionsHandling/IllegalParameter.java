package docvel.functionalDiagnostics.exceptionsHandling;

public class InvalidResource extends RuntimeException{

    public InvalidResource(String message) {
        super(message);
    }
}
