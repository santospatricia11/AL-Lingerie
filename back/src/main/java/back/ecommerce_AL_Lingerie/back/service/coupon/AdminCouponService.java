package back.ecommerce_AL_Lingerie.back.service.coupon;

import back.ecommerce_AL_Lingerie.back.model.Coupon;

import java.util.List;


public interface AdminCouponService {
	Coupon createCoupon(Coupon coupon);
	List<Coupon> getAllCoupon();
}
