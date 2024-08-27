package back.ecommerce_AL_Lingerie.back.services.customer.review;

import back.ecommerce_AL_Lingerie.back.dto.OrderedProductsResponseDto;
import back.ecommerce_AL_Lingerie.back.dto.ReviewDto;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface ReviewService {
	OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId);
	
	ReviewDto giveReview(ReviewDto reviewDto) throws IOException ;
}
