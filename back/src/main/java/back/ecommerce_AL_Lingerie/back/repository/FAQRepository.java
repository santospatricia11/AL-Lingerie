package back.ecommerce_AL_Lingerie.back.repository;

import back.ecommerce_AL_Lingerie.back.model.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Long> {

    List<FAQ> findAllByProductId(Long productId);

}
