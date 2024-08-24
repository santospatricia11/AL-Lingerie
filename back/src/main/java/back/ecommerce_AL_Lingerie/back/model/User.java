package back.ecommerce_AL_Lingerie.back.model;

import back.ecommerce_AL_Lingerie.back.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="users")
public class User {

    @Id
    //@Generated(stra= GenerationType.IDENTITY)
    private Long id;



    private String email;
    private String password;
    private String name;
    private UserRole role;
    private byte[]img;


}
