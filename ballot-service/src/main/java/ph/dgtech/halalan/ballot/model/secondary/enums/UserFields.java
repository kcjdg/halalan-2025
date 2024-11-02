package ph.dgtech.halalan.ballot.model.secondary.enums;

public enum UserFields {
    MIDDLE_NAME("middleName"),
    BARANGAY("barangay"),
    REGION("region"),
    PROVINCE("province"),
    MUNICIPALITY("municipality"),
    VOTER_ID("voterId"),
    GENDER("gender");

    private final String fieldName;

    UserFields(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
