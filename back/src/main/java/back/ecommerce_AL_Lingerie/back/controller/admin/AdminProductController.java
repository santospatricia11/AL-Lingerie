package back.ecommerce_AL_Lingerie.back.controller.admin;



import back.ecommerce_AL_Lingerie.back.dto.FAQDto;
import back.ecommerce_AL_Lingerie.back.dto.ProductDto;
import back.ecommerce_AL_Lingerie.back.services.admin.adminproduct.AdminProductService;
import back.ecommerce_AL_Lingerie.back.services.admin.faq.FAQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminProductController {

	private final AdminProductService adminProductService;

	@PostMapping("/product")
	public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
		try {
			// Adicionar o produto
			ProductDto createdProduct = adminProductService.addProduct(productDto);
			return ResponseEntity.ok(createdProduct);
		} catch (Exception e) {
			log.error("Error adding product", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id) {
		ProductDto productDto = adminProductService.getProductById(id);
		if (productDto != null) {
			return new ResponseEntity<>(productDto, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/products")
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		System.out.println("cheguei AQUI");
		try {
			List<ProductDto> products = adminProductService.getAllProducts();
			return ResponseEntity.ok(products);
        } catch (Exception e) {
			log.error("Error fetching products", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}



	@DeleteMapping("/product/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
		boolean isDeleted = adminProductService.deleteProduct(id);
		if (isDeleted) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
