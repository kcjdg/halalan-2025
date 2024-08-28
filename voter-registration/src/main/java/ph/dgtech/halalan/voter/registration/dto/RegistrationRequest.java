package ph.dgtech.halalan.voter.registration.dto;

public record RegistrationRequest(String voterId, String username, String email, String firstName, String lastName, String password) {

}
