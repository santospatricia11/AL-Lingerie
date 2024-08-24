package back.ecommerce_AL_Lingerie.back.repository;

import back.ecommerce_AL_Lingerie.back.enums.UserRole;
import back.ecommerce_AL_Lingerie.back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmail(String email);

    Optional<User> findByRole(UserRole userRole);
}
