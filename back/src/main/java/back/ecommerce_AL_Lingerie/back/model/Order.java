package back.ecommerce_AL_Lingerie.back.model;

import back.ecommerce_AL_Lingerie.back.dto.OrderDto;
import back.ecommerce_AL_Lingerie.back.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderDescription;

    private Date date;

    private Long amount;

    private String address;

    private String payment;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "order_status", length = 25)
    private OrderStatus orderStatus;

    private Long totalAmount;

    private Long discount;

    private UUID trackingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", referencedColumnName = "id")
    private Coupon coupon;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    private List<CartItems> cartItems;

    // Método para converter a entidade Order para OrderDto
    public OrderDto getOrderDto() {
        return OrderDto.builder()
                .id(this.id)
                .orderDescription(this.orderDescription)
                .date(this.date)
                .amount(this.amount)
                .address(this.address)
                .totalAmount(this.totalAmount)
                .discount(this.discount)
                .payment(this.payment)
                .orderStatus(this.orderStatus)
                .trackingId(this.trackingId)
                .userName(this.user != null ? this.user.getName() : null)
                .couponName(this.coupon != null ? this.coupon.getName() : null)
                .build();
    }
}
