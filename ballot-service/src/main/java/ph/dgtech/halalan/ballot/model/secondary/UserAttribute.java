package ph.dgtech.halalan.ballot.model.secondary;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_attribute")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserAttribute {

    @Id
    private String id;
    private String userId;
    private String name;
    private String value;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
