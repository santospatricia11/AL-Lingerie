package back.ecommerce_AL_Lingerie.back.repository;

import back.ecommerce_AL_Lingerie.back.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProductId(Long productId);

}
