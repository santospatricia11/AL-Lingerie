package back.ecommerce_AL_Lingerie.back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ReviewDto {

    private Long id;

    private Long rating;

    private String description;

    private Long userId;

    private Long productId;

    private String username;
}
