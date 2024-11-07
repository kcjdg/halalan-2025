package ph.dgtech.halalan.ballot.model.secondary;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "user_entity")
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "attributes")
public class User {

    @Id
    private String id;
    private String username;
    private String email;
    private Boolean enabled;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "REALM_ID")
    private String realmId;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "user_attribute",
            joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "name")
    @Column(name = "value")
    private Map<String, String> attributes = new HashMap<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(enabled, user.enabled) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(realmId, user.realmId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, enabled, firstName, lastName, realmId);
    }


}
