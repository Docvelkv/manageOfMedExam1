package docvel.functionalDiagnostics.exceptionsHandling;

public class IllegalParameter extends RuntimeException{

    public IllegalParameter(String message) {
        super(message);
    }
}