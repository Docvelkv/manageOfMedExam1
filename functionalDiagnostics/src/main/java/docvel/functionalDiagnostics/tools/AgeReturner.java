package docvel.functionalDiagnostics.tools;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Arrays;

public class DateTime {

    /**
     * Получение даты
     * @param date строка формата "дд.мм.гггг"
     * @return LocalDate
     */
    public static LocalDate getDateFromString(String date){
        int[] array = Arrays.stream(date.split("\\."))
                .mapToInt(Integer::parseInt).toArray();
        return LocalDate.of(array[2], array[1], array[0]);
    }

    /**
     * Получение даты и времени
     * @param dateTime строка формата "дд.мм.гггг.чч.мм"
     * @return LocalDateTime
     */
    public static LocalDateTime getDateTimeFromString(String dateTime){
        int[] array = Arrays.stream(dateTime.split("\\."))
                .mapToInt(Integer::parseInt).toArray();
        return LocalDateTime.of(array[2], array[1], array[0], array[3], array[4]);
    }

    /**
     * Получение полного возраста в виде массива чисел
     * @param dateOfBirth дата рождения (дд.мм.гггг)
     * @return long[годы, месяцы, дни]
     */
    public static long[] getFullAge(String dateOfBirth) {
        Period period = Period.between(DateTime.getDateFromString(dateOfBirth), LocalDate.now());
        long[] fullAge = new long[3];
        fullAge[0] = period.getYears();
        fullAge[1] = period.getMonths();
        fullAge[2] = period.getDays();
        return fullAge;
    }

    /**
     * Получение возраста в виде строки с единицами измерения
     * @param age возраст (массив из трёх элементов[годы, месяцы, дни])
     * @return String (в виде "X лет X месяцев X дней")
     */
    public static String getAgeInUnits(long[] age){
        long lastDigitOfAge;
        String[] ageUnits = new String[3];

        if(age[0] != 0) {
            lastDigitOfAge = age[0] % 10;
            ageUnits[0] = String.format("%d лет", age[0]);
            if (lastDigitOfAge == 1 && age[0] != 11) {
                ageUnits[0] = String.format("%d год", age[0]);
            }
            if ((lastDigitOfAge == 2 && age[0] != 12) ||
                    (lastDigitOfAge == 3 && age[0] != 13) ||
                    (lastDigitOfAge == 4 && age[0] != 14)) {
                ageUnits[0] = String.format("%d года", age[0]);
            }
        }else ageUnits[0] = "";

        if(age[1] != 0){
            ageUnits[1] = String.format("%d месяцев", age[1]);
            if(age[1] == 1) {
                ageUnits[1] = String.format("%d месяц", age[1]);
            }
            if(age[1] == 2 || age[1] == 3 || age[1] == 4) {
                ageUnits[1] = String.format("%d месяца", age[1]);
            }
        }else ageUnits[1] = "";

        if(age[2] != 0){
            lastDigitOfAge = age[2] % 10;
            ageUnits[2] = String.format("%d дней", age[2]);
            if(lastDigitOfAge == 1 && age[2] != 11) {
                ageUnits[2] = String.format("%d день", age[2]);
            }
            if((lastDigitOfAge == 2 && age[2] != 12) ||
                    (lastDigitOfAge == 3 && age[2] != 13) ||
                    (lastDigitOfAge == 4 && age[2] != 14)) {
                ageUnits[2] = String.format("%d дня", age[2]);
            }
        }else ageUnits[2] = "";
        return String.join(" ", ageUnits).trim();
    }
}
