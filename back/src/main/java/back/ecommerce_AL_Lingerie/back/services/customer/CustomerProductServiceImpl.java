package back.ecommerce_AL_Lingerie.back.services.customer;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import back.ecommerce_AL_Lingerie.back.dto.ProductDetailDto;
import back.ecommerce_AL_Lingerie.back.dto.ProductDto;
import back.ecommerce_AL_Lingerie.back.model.FAQ;
import back.ecommerce_AL_Lingerie.back.model.Product;
import back.ecommerce_AL_Lingerie.back.model.Review;
import back.ecommerce_AL_Lingerie.back.repository.FAQRepository;
import back.ecommerce_AL_Lingerie.back.repository.ProductRepository;
import back.ecommerce_AL_Lingerie.back.repository.ReviewRepository;
import org.springframework.stereotype.Service;



import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerProductServiceImpl implements CustomerProductService {

	private final ProductRepository productRepository;
	private final FAQRepository faqRepository;
	private final ReviewRepository reviewRepository;

	public List<ProductDto> getAllProducts() {
		log.info("Fetching all products");
		List<Product> products = productRepository.findAll();
		return products.stream().map(Product::getDto).collect(Collectors.toList());
	}

	public List<ProductDto> getAllProductsByName(String name) {
		log.info("Fetching products by name containing '{}'", name);
		List<Product> products = productRepository.findAllByNameContaining(name);
		return products.stream().map(Product::getDto).collect(Collectors.toList());
	}

	public ProductDetailDto getProductDetailById(Long productId) {
		log.info("Fetching product details for product with ID '{}'", productId);
		Optional<Product> optionalProduct = productRepository.findById(productId);
		if (optionalProduct.isPresent()) {
			List<FAQ> faqs = faqRepository.findAllByProductId(productId);
			List<Review> reviews = reviewRepository.findAllByProductId(productId);

			ProductDetailDto productDetailDto = new ProductDetailDto();
			productDetailDto.setProductDto(optionalProduct.get().getDto());
			productDetailDto.setFaqDtoList(faqs.stream().map(FAQ::getFAQDto).collect(Collectors.toList()));
			productDetailDto.setReviewDtoList(reviews.stream().map(Review::getDto).collect(Collectors.toList()));

			return productDetailDto;
		}
		log.error("Product with ID '{}' not found", productId);
		return null;
	}
}
