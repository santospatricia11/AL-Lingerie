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

    //private byte[] byteImg;
    private String imageUrl;  // Verifique se este campo está presente

    private Long categoryId;

    private String categoryName;

   // private MultipartFile img;

    private Long quantity;

}
