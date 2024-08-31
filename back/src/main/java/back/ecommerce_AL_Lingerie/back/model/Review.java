package back.ecommerce_AL_Lingerie.back.model;

import back.ecommerce_AL_Lingerie.back.dto.ReviewDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long rating;
    private String imageUrl;

    private String description;

  /*  @Lob
    @Column(name = "img", columnDefinition = "bytea")
    private byte[] img;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Método para converter a entidade Review para ReviewDto
    public ReviewDto getDto() {
        return ReviewDto.builder()
                .id(this.id)
                .rating(this.rating)
                .description(this.description)
                .imageUrl(this.imageUrl)
                .productId(this.product.getId())
                .userId(this.user.getId())
                .username(this.user.getName())
                .build();
    }
}
