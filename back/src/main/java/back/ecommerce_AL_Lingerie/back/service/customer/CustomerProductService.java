package back.ecommerce_AL_Lingerie.back.service.customer;

import back.ecommerce_AL_Lingerie.back.dto.ProductDetailDto;
import back.ecommerce_AL_Lingerie.back.dto.ProductDto;

import java.util.List;



public interface CustomerProductService {
	List<ProductDto> getAllProducts();

	List<ProductDto> getAllProductsByName(String name);
	
	ProductDetailDto getProductDetailById(Long productId);

}
