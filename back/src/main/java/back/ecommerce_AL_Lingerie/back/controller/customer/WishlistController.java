package back.ecommerce_AL_Lingerie.back.controller.customer;


import back.ecommerce_AL_Lingerie.back.dto.WishlistDto;
import back.ecommerce_AL_Lingerie.back.service.customer.wishlist.WishlistService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
@Slf4j
public class WishlistController {
	private final WishlistService wishlistService;

	@PostMapping("/wishlist")
	public ResponseEntity<?> addProductToWishlist(@RequestBody WishlistDto wishlistDto) {
		log.info("Received request to add product to wishlist for user with ID: {}", wishlistDto.getUserId());
		WishlistDto postedWishlistDto = wishlistService.addProductToWishlist(wishlistDto);
		if (postedWishlistDto == null) {
			log.warn("Failed to add product to wishlist for user with ID: {}", wishlistDto.getUserId());
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Something went wrong");
		}
		log.info("Product added to wishlist successfully for user with ID: {}", wishlistDto.getUserId());
		return ResponseEntity.status(HttpStatus.CREATED).body(postedWishlistDto);
	}

	@GetMapping("/wishlist/{userId}")
	public ResponseEntity<List<WishlistDto>> getWishlistByUserId(@PathVariable Long userId) {
		log.info("Received request to get wishlist for user with ID: {}", userId);
		List<WishlistDto> wishlistDtos = wishlistService.getWishlistByUserId(userId);
		return ResponseEntity.ok(wishlistDtos);
	}
}
