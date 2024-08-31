package back.ecommerce_AL_Lingerie.back.model;

import back.ecommerce_AL_Lingerie.back.dto.CategoryDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Builder
@Table(name="category")
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    private String description;

    public CategoryDto getDto(){
        return CategoryDto.builder()
                .id(id)
                .description(description)
                .name(name)
                .build();
    }
}
