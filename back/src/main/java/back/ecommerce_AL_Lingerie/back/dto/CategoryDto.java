package back.ecommerce_AL_Lingerie.back.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
}
