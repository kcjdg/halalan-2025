package ph.dgtech.halalan.voter.profile.dto.info;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import ph.dgtech.halalan.voter.profile.dto.validation.MinAge;

import java.time.LocalDate;

public record PersonalInfo(
        @NotNull
        String firstName,
        @NotNull
        String middleName,
        @NotNull
        String lastName,
        @NotNull
        String gender,
        @NotNull
        @Past
        @MinAge(18)
        LocalDate dob,
        @Email
        String email
        )
{
        public enum Gender {
                MALE("M"),
                FEMALE("F");
                private String code;

                Gender(String code) {
                        this.code = code;
                }

                public String getCode() {
                        return code;
                }

                public static Gender fromString(String gender) {
                        return switch (gender.toUpperCase()) {
                                case "M" -> Gender.MALE;
                                case "F" -> Gender.FEMALE;
                                default -> throw new IllegalArgumentException("Unspecified gender");
                        };
                }
        }
}
