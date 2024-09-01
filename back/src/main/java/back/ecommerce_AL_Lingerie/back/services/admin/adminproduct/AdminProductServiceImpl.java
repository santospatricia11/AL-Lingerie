package back.ecommerce_AL_Lingerie.back.services.admin.adminproduct;


import back.ecommerce_AL_Lingerie.back.dto.ProductDto;
import back.ecommerce_AL_Lingerie.back.model.Category;
import back.ecommerce_AL_Lingerie.back.model.Product;
import back.ecommerce_AL_Lingerie.back.repository.CategoryRepository;
import back.ecommerce_AL_Lingerie.back.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminProductServiceImpl implements AdminProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ProductDto addProduct(ProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .imageUrl(productDto.getImageUrl())
                .category(categoryRepository.findById(productDto.getCategoryId())
                        .orElseThrow(() -> new RuntimeException("Category not found")))  // Verificando e atribuindo a categoria
                .build();

        Product savedProduct = productRepository.save(product);
        return savedProduct.getDto();
    }

    @Override
    public List<ProductDto> getAllProducts() {
        log.info("Fetching all products.");
        try {
            List<Product> products = productRepository.findAll();
            log.info("Number of products retrieved: {}", products.size());
            List<ProductDto> productDtos = products.stream()
                    .map(product -> {
                        try {
                            return product.getDto();
                        } catch (Exception e) {
                            log.error("Error converting product to DTO", e);
                            return null; // Pode ser necessário lidar com produtos nulos posteriormente
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            log.info("Products converted to DTOs.");
            return productDtos;
        } catch (Exception e) {
            log.error("Error fetching products", e);
            throw e;
        }
    }


    @Override
    public List<ProductDto> getAllProductsByName(String name) {
        log.info("Fetching all products by name: {}", name);
        List<Product> products = productRepository.findAllByNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    @Override
    public boolean deleteProduct(Long id) {
        log.info("Deleting product with ID: {}", id);
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public ProductDto getProductById(Long productId) {
        log.info("Fetching product with ID: {}", productId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.map(Product::getDto).orElse(null);
    }


    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        log.info("Updating product with ID: {}", productId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());

        if (optionalProduct.isPresent() && optionalCategory.isPresent()) {
            Product product = optionalProduct.get().toBuilder()
                    .name(productDto.getName())
                    .price(productDto.getPrice())
                    .description(productDto.getDescription())
                    .imageUrl(productDto.getImageUrl())
                    .category(optionalCategory.get())
                    .build();
            Product savedProduct = productRepository.save(product);
            return savedProduct.getDto();
        }
        return null;
    }
}
