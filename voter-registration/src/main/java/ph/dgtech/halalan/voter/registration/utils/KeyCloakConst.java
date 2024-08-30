package ph.dgtech.halalan.voter.registration.utils;

public final class KeyCloakConst {
    public enum Groups {
        VOTER("voter-group"),
        ADMIN("admin-group"),
        OBSERVER("observer-group"),
        OFFICIAL("election-official-group");
        private final String value;

        Groups(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }
    public enum REGION {
        NCR("NCR"),
        CAR("CAR"),
        I("I"),
        II("II"),
        III("III"),
        IV_A("IV-A"),
        IV_B("IV-B"),
        V("V"),
        VI("VI"),
        VII("VII"),
        VIII("VIII"),
        IX("IX"),
        X("X"),
        XI("XI"),
        XII("XII"),
        XIII("XIII"),
        BARMM("BARMM");
        private final String value;

        REGION(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
        public String getGroupCode(){
            return Groups.VOTER.getValue() + "/" + value;
        }
    }


}
