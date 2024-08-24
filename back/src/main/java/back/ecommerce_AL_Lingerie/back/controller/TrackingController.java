package back.ecommerce_AL_Lingerie.back.controller;

import java.util.UUID;

import back.ecommerce_AL_Lingerie.back.dto.OrderDto;
import back.ecommerce_AL_Lingerie.back.service.customer.cart.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TrackingController {
	private final CartService cartService;

	@GetMapping("/order/{trackingId}")
	public ResponseEntity<OrderDto> searchOrderByTrackingId(@PathVariable UUID trackingId) {
		log.info("Received request to search order by tracking ID: {}", trackingId);
		OrderDto orderDto = cartService.searchOrderByTrackingId(trackingId);
		if (orderDto == null) {
			log.warn("Order not found for tracking ID: {}", trackingId);
			return ResponseEntity.notFound().build();
		}
		log.info("Found order for tracking ID: {}", trackingId);
		return ResponseEntity.ok(orderDto);
	}
}
