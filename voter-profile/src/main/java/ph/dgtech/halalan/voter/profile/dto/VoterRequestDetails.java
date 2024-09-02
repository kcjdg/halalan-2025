package ph.dgtech.halalan.voter.profile.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import ph.dgtech.halalan.voter.profile.dto.validation.MinAge;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

public record VoterRequestDetails(
        @NotNull
        String voterId,
        @NotNull
        String firstName,
        @NotNull
        String middleName,
        @NotNull
        String lastName,
        @NotNull
        @Past
        @MinAge(18)
        LocalDate dob,
        @NotNull
        String gender,
        @Email
        String email,
        String photo,
        String nationalIdNumber,
        String voterRegistrationNumber,
        String passportNumber,
        String driversLicenseNumber,
        String phoneNumber,
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String postalCode,
        String country,
        String pollingStation,
        String votingDistrict,
        String electoralDivision,
        String username,
        String password,
        boolean twoFactorAuthenticationEnabled,
        List<SecurityQuestion> securityQuestions,
        ZonedDateTime registrationDate,
        ZonedDateTime lastLogin,
        String voterStatus,
        String votingStatus,
        String votingMethod,
        boolean consentForCommunication,
        String createdBy,
        String updatedBy,
        String notes
) {
    public record SecurityQuestion(String question, String answerHash) {
    }

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