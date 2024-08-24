package back.ecommerce_AL_Lingerie.back.service.customer.review;

import back.ecommerce_AL_Lingerie.back.dto.OrderedProductsResponseDto;
import back.ecommerce_AL_Lingerie.back.dto.ReviewDto;

import java.io.IOException;



public interface ReviewService {
	OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId);
	
	ReviewDto giveReview(ReviewDto reviewDto) throws IOException ;
}
