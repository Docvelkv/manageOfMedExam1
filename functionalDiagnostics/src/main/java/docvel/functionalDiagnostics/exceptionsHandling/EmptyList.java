package docvel.registry.exceptionsHandling;

public class EmptyList extends RuntimeException{
    public EmptyList(String message){
        super(message);
    }
}
