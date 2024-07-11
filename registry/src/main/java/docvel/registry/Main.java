package docvel.registry;

public class Main {
    public static void main(String[] args) {
       String regexp = "([А-ЯЁа-яё]\\s?)+(\\s№\\d)?";
       String data = "Кардиология";
        System.out.println(data.matches(regexp));
    }
}