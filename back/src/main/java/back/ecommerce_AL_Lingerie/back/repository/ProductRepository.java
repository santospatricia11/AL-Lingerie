package back.ecommerce_AL_Lingerie.back.repository;

import back.ecommerce_AL_Lingerie.back.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long> {

    List<Product> findAllByNameContaining(String title);

}
