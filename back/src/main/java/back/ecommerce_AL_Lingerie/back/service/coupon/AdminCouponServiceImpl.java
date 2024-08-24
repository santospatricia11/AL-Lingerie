package back.ecommerce_AL_Lingerie.back.service.coupon;

import java.util.List;

import back.ecommerce_AL_Lingerie.back.exceptions.ValidationException;
import back.ecommerce_AL_Lingerie.back.model.Coupon;
import back.ecommerce_AL_Lingerie.back.repository.CouponRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;



import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCouponServiceImpl implements AdminCouponService {

	private final CouponRepository couponRepository;

	public Coupon createCoupon(Coupon coupon) {
		if(couponRepository.existsByCode(coupon.getCode())) {
			throw new ValidationException("Coupon code already exists");
		} else {
			log.info("New Coupon Code Added: {}", coupon.getCode());
			return couponRepository.save(coupon);
		}
	}

	public List<Coupon> getAllCoupon() {
		log.info("Fetching all coupons.");
		return couponRepository.findAll();
	}
}
