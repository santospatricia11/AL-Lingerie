package back.ecommerce_AL_Lingerie.back.controller.admin;

import back.ecommerce_AL_Lingerie.back.exceptions.ValidationException;
import back.ecommerce_AL_Lingerie.back.model.Coupon;
import back.ecommerce_AL_Lingerie.back.services.admin.coupon.AdminCouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/api/admin/coupons")
@RequiredArgsConstructor
@Slf4j
public class AdminCouponController {

	private final AdminCouponService adminCouponService;

	@PostMapping
	public ResponseEntity<?> createCoupon(@RequestBody Coupon coupon) {
		log.info("Received request to create coupon with code: {}", coupon.getCode());
		if (coupon.getCode() == null) {
			log.warn("Coupon code is empty");
			return ResponseEntity.status(HttpStatus.CONFLICT).body("codeEmpty");
		}
		try {
			log.info("Creating coupon: {}", coupon);
			Coupon createdCoupon = adminCouponService.createCoupon(coupon);
			log.info("Coupon created with ID: {}", createdCoupon.getId());
			return ResponseEntity.ok(createdCoupon);
		} catch (ValidationException e) {
			log.error("Validation error while creating coupon: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<List<Coupon>> getAllCoupon() {
		log.info("Received request to get all coupons");
		List<Coupon> coupons = adminCouponService.getAllCoupon();
		log.info("Returning {} coupons", coupons.size());
		return ResponseEntity.ok(coupons);
	}
}
