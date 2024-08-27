package back.ecommerce_AL_Lingerie.back.model;

import back.ecommerce_AL_Lingerie.back.enums.UserRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;
@Builder
@Entity
@Data
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String name;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User() {
    }

    public User(Long id, String email, String password, String name, UserRole role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }
/*  @Lob
    @Column(name = "img", columnDefinition = "bytea")
    private byte[] img;*/

}
