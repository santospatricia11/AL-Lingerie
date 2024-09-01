package back.ecommerce_AL_Lingerie.back.services.customer.cart;

import java.util.*;

import java.util.stream.Collectors;

import back.ecommerce_AL_Lingerie.back.dto.AddProductInCartDto;
import back.ecommerce_AL_Lingerie.back.dto.CartItemsDto;
import back.ecommerce_AL_Lingerie.back.dto.OrderDto;
import back.ecommerce_AL_Lingerie.back.dto.PlaceOrderDto;
import back.ecommerce_AL_Lingerie.back.enums.OrderStatus;
import back.ecommerce_AL_Lingerie.back.exceptions.ValidationException;
import back.ecommerce_AL_Lingerie.back.model.*;
import back.ecommerce_AL_Lingerie.back.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;




@Service
@AllArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

	@Autowired
	private final OrderRepository orderRepository;

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private final CartItemsRepository cartItemsRepository;

	@Autowired
	private final ProductRepository productRepository;

	@Autowired
	private final CouponRepository couponRepository;

	public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {
		// Encontre a ordem ativa do usuário
		Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.PENDING);

		if (activeOrder == null) {
			// Se não houver uma ordem ativa, crie uma nova
			Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());
			if (optionalUser.isPresent()) {
				activeOrder = new Order();
				activeOrder.setUser(optionalUser.get());
				activeOrder.setAmount(0L);
				activeOrder.setTotalAmount(0L);
				activeOrder.setDiscount(0L);
				activeOrder.setOrderStatus(OrderStatus.PENDING);
				// Inicializa a lista de cartItems
				activeOrder.setCartItems(new ArrayList<>());
				orderRepository.save(activeOrder);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
			}
		}


		Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
				addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId());

		if (optionalCartItems.isPresent()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Product already in cart");
		}


		Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
		if (optionalProduct.isPresent()) {
			Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());
			if (!optionalUser.isPresent()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
			}

			CartItems cartItems = new CartItems();
			cartItems.setProduct(optionalProduct.get());
			cartItems.setPrice(optionalProduct.get().getPrice());
			cartItems.setQuantity(1L);
			cartItems.setUser(optionalUser.get());
			cartItems.setOrder(activeOrder);

			CartItems updatedCart = cartItemsRepository.save(cartItems);


			if (activeOrder.getCartItems() == null) {
				activeOrder.setCartItems(new ArrayList<>());
			}
			activeOrder.getCartItems().add(updatedCart);

			activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cartItems.getPrice());
			activeOrder.setAmount(activeOrder.getAmount() + cartItems.getPrice());

			orderRepository.save(activeOrder);

			return ResponseEntity.status(HttpStatus.CREATED).body(cartItems);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
		}
	}

	public OrderDto getCartByUserId(Long userId) {
		Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);

		if (activeOrder == null)
			return null;
		List<CartItemsDto> cartItemsDtosList = activeOrder.getCartItems().stream().map(CartItems::getCartDto)
				.collect(Collectors.toList());

		OrderDto orderDto = new OrderDto();
		orderDto.setId(activeOrder.getId());
		orderDto.setAmount(activeOrder.getAmount());
		orderDto.setOrderStatus(activeOrder.getOrderStatus());
		orderDto.setDiscount(activeOrder.getDiscount());
		orderDto.setTotalAmount(activeOrder.getTotalAmount());
		orderDto.setCartItems(cartItemsDtosList);
		if (activeOrder.getCoupon() != null)
			orderDto.setCouponCode(activeOrder.getCoupon().getCode());

		if (activeOrder.getCoupon() != null) {
			orderDto.setCouponName(activeOrder.getCoupon().getName());
		}

		return orderDto;
	}

	public OrderDto applyCoupon(Long userId, String code) {
		Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
		Coupon coupon = couponRepository.findByCode(code)
				.orElseThrow(() -> new ValidationException("coupon not found"));
		if (couponIsExpired(coupon)) {
			throw new ValidationException("coupon is expired");
		}
		double discountAmount = ((coupon.getDiscount() / 100.0) * activeOrder.getTotalAmount());
		double netAmount = activeOrder.getTotalAmount() - discountAmount;

		activeOrder.setAmount((long) netAmount);
		activeOrder.setDiscount((long) discountAmount);
		activeOrder.setCoupon(coupon);

		orderRepository.save(activeOrder);
		return activeOrder.getOrderDto();

	}

	public boolean couponIsExpired(Coupon coupon) {
		Date currentDate = new Date();
		Date expirationDate = coupon.getExpirationDate();
		return expirationDate != null && currentDate.after(expirationDate);
	}

	public OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto) {
		Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.PENDING);
		if (activeOrder == null) {
			return null;
		}

		Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
		if (optionalProduct.isEmpty()) {
			return null;
		}

		Optional<CartItems> optionalCartItem = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
				optionalProduct.get().getId(), activeOrder.getId(), addProductInCartDto.getUserId());

		if (optionalCartItem.isPresent()) {
			CartItems cartItems = optionalCartItem.get();
			Product product = optionalProduct.get();

			activeOrder.setAmount(activeOrder.getAmount() + product.getPrice());
			activeOrder.setTotalAmount(activeOrder.getTotalAmount() + product.getPrice());

			cartItems.setQuantity(cartItems.getQuantity() + 1);

			if (activeOrder.getCoupon() != null) {
				double discountAmount = ((activeOrder.getCoupon().getDiscount() / 100.0) * activeOrder.getTotalAmount());
				double netAmount = activeOrder.getTotalAmount() - discountAmount;

				activeOrder.setAmount((long) netAmount);
				activeOrder.setDiscount((long) discountAmount);
			}

			cartItemsRepository.save(cartItems);
			orderRepository.save(activeOrder);
			return activeOrder.getOrderDto();
		}
		return null;
	}

	public OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto) {
		Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.PENDING);
		if (activeOrder == null) {
			return null;
		}

		Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
		if (optionalProduct.isEmpty()) {
			return null;
		}

		Optional<CartItems> optionalCartItem = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
				optionalProduct.get().getId(), activeOrder.getId(), addProductInCartDto.getUserId());

		if (optionalCartItem.isPresent()) {
			CartItems cartItems = optionalCartItem.get();
			Product product = optionalProduct.get();

			activeOrder.setAmount(activeOrder.getAmount() - product.getPrice());
			activeOrder.setTotalAmount(activeOrder.getTotalAmount() - product.getPrice());

			cartItems.setQuantity(cartItems.getQuantity() - 1);

			if (activeOrder.getCoupon() != null) {
				double discountAmount = ((activeOrder.getCoupon().getDiscount() / 100.0) * activeOrder.getTotalAmount());
				double netAmount = activeOrder.getTotalAmount() - discountAmount;

				activeOrder.setAmount((long) netAmount);
				activeOrder.setDiscount((long) discountAmount);
			}

			cartItemsRepository.save(cartItems);
			orderRepository.save(activeOrder);
			return activeOrder.getOrderDto();
		}
		return null;
	}

	public OrderDto placedOrder(PlaceOrderDto placeOrderDto) {
		Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.PENDING);

		if (activeOrder == null) {
			log.info("There is no active pending order for the user with userId: {}", placeOrderDto.getUserId());
			return null;
		}

		Optional<User> optionalUser = userRepository.findById(placeOrderDto.getUserId());
		if (optionalUser.isPresent()) {
			activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());

			activeOrder.setAddress(placeOrderDto.getAddress());
			activeOrder.setDate(new Date());
			activeOrder.setOrderStatus(OrderStatus.PENDING);
			activeOrder.setTrackingId(UUID.randomUUID());

			orderRepository.save(activeOrder);
			log.info("Updated order saved with ID: {}", activeOrder.getId());


			Order newOrder = new Order();
			newOrder.setAmount(0L);
			newOrder.setTotalAmount(0L);
			newOrder.setDiscount(0L);
			newOrder.setUser(optionalUser.get());
			newOrder.setOrderStatus(OrderStatus.PENDING);

			orderRepository.save(newOrder);
			log.info("new pending order created : {}",newOrder.toString());

			return activeOrder.getOrderDto();
		}
		return null;
	}

	public List<OrderDto> getMyPlacedOrders(Long userId) {
		return orderRepository
				.findByUserIdAndOrderStatusIn(userId,
						List.of(OrderStatus.SHIPPED, OrderStatus.PENDING, OrderStatus.DELIVERED))
				.stream().map(Order::getOrderDto).collect(Collectors.toList());
	}

	public OrderDto searchOrderByTrackingId(UUID trackingId) {
		Optional<Order> optionalOrder = orderRepository.findByTrackingId(trackingId);
        return optionalOrder.map(Order::getOrderDto).orElse(null);
    }

}
