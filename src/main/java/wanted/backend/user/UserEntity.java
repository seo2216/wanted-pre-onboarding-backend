package wanted.backend.user;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Integer id;

    @Column(
            nullable = false
    )
    private String userEmail;

    @Column(
            nullable = false
    )
    private String password;
}
