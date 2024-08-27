package back.ecommerce_AL_Lingerie.back.services.customer.wishlist;


import back.ecommerce_AL_Lingerie.back.dto.WishlistDto;

import java.util.List;

public interface WishlistService {
	WishlistDto addProductToWishlist(WishlistDto wishlistDto);
	
	List<WishlistDto> getWishlistByUserId(Long userId);
}
