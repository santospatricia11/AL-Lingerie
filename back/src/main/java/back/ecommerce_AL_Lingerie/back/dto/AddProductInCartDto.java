package back.ecommerce_AL_Lingerie.back.dto;

import lombok.Data;

@Data
public class AddProductInCartDto {

    private Long userId;
    private Long productId;
}
