package back.ecommerce_AL_Lingerie.back.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {


    private Long id;

    private String name;

    private Long price;

    private String description;


    private String imageUrl;
    private Long categoryId;

    private String categoryName;


    private Long quantity;

}
