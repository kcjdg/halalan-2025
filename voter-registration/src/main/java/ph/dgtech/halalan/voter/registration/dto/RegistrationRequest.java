package ph.dgtech.halalan.voter.registration.dto;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

public record RegistrationRequest(
        String voterId,
        String firstName,
        String middleName,
        String lastName,
        LocalDate dob,
        String gender,
        String photo,
        String nationalIdNumber,
        String voterRegistrationNumber,
        String passportNumber,
        String driversLicenseNumber,
        String email,
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
           return switch (gender.toUpperCase()){
                case "M" -> Gender.MALE;
                case "F" -> Gender.FEMALE;
                default -> throw new IllegalArgumentException("Unspecified gender");
            };
        }
    }
}