package back.ecommerce_AL_Lingerie.back.services.customer;


import back.ecommerce_AL_Lingerie.back.dto.ProductDetailDto;
import back.ecommerce_AL_Lingerie.back.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CustomerProductService {
	List<ProductDto> getAllProducts();

	List<ProductDto> getAllProductsByName(String name);
	
	ProductDetailDto getProductDetailById(Long productId);

}
