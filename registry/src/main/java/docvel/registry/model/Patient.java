package docvel.registrationOffice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@NoArgsConstructor
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private long id;

    @NotEmpty
    @Pattern(regexp = "^[0-9]{3}-[0-9]{3}-[0-9]{3}")
    @Column(name = "snils")
    private String snils;

    @NotBlank
    @Pattern(regexp = "^[а-яёА-ЯЁ]+$",
            message = "Введены неалфавитные символы")
    @Column(name = "first_name")
    private String firstName;

    @Pattern(regexp = "^([а-яёА-ЯЁ]+)?$",
            message = "Введены неалфавитные символы")
    @Column(name = "surname")
    private String surname;

    @NotBlank
    @Pattern(regexp = "^[а-яёА-ЯЁ]+$",
            message = "Введены неалфавитные символы")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private Gender gender;

    @NotBlank
    @Pattern(regexp = "^\\d{2}\\.\\d{2}\\.(19|20)?\\d{2}$",
            message = "Не соответствует формату даты: дд.мм.гггг")
    @Column(name = "date_of_birth")
    private String dateOfBirth = getDateOfBirth();

    @EqualsAndHashCode.Exclude
    @Column(name = "ageInUnit")
    private String ageInUnits;

    @EqualsAndHashCode.Exclude
    @Column(name = "ageGroup")
    private int ageGroup;

    public Patient(String firstName, String surname, String lastName,
                   Gender gender, String dateOfBirth) {
        this.firstName = firstName;
        this.surname = surname;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.ageInUnits = getAgeInUnits(getFullAge(dateOfBirth));
        this.ageGroup = getAgeGroup(getFullAge(dateOfBirth), getGender());
    }

    /**
     * Получение полного возраста в виде массива чисел
     * @param dateOfBirth дата рождения (дд.мм.гггг)
     * @return long[] ([г,м,д])
     */
    public static long[] getFullAge(String dateOfBirth) {
        LocalDate date = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        Period period = Period.between(date, LocalDate.now());
        long[] fullAge = new long[3];
        fullAge[0] = period.getYears();
        fullAge[1] = period.getMonths();
        fullAge[2] = period.getDays();
        return fullAge;
    }

    /**
     * Получение возраста в виде строки с единицами измерения
     * @param age возраст (массив[годы, месяцы, дни])
     * @return String (в виде X лет X месяцев X дней)
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

    /**
     * Получение возрастной группы (для отчётности)
     * @param age возраст (массив[годы, месяцы, дни])
     * @param gender пол
     * @return int (номер возрастной группы)
     */
    public static int getAgeGroup(long[] age, Gender gender){
        if(age[0] == 0 && age[1] == 0 && age[2] > 0 && age[2] < 7){
            return 1;
        }
        if(age[0] == 0 && (age[1] != 0 || age[2] >= 7)){
            return 2;
        }
        if(age[0] >= 1 && age[0] < 4){
            return 3;
        }
        if(age[0] >= 4 && age[0] < 8){
            return 4;
        }
        if((age[0] >= 8 && age[0] < 13 && gender == Gender.male) ||
                (age[0] >= 8 && age[0] < 12 && gender == Gender.female)){
            return 5;
        }
        if((age[0] >= 13 && age[0] < 17 && gender == Gender.male) ||
                (age[0] >= 12 && age[0] < 16 && gender == Gender.female)){
            return 6;
        }
        if((age[0] >= 17 && age[0] < 22 && gender == Gender.male) ||
                (age[0] >= 16 && age[0] < 21 && gender == Gender.female)) {
            return 7;
        }
        if((age[0] >= 22 && age[0] < 36 && gender == Gender.male) ||
                (age[0] >= 21 && age[0] < 36 && gender == Gender.female)){
            return 8;
        }
        if((age[0] >= 36 && age[0] < 61 && gender == Gender.male) ||
                (age[0] >= 36 && age[0] < 56 && gender == Gender.female)){
            return 9;
        }
        if((age[0] >= 61 && age[0] < 75 && gender == Gender.male) ||
                (age[0] >= 56 && age[0] < 75 && gender == Gender.female)){
            return 10;
        }
        if(age[0] >= 75 && age[0] < 90) {
            return 11;
        }
        return 12;
    }
}
