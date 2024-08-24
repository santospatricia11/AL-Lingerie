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

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
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

    private OrderStatus orderStatus;

    private Long totalAmount;

    private Long discount;

    private UUID trackingId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "coupon_id", referencedColumnName = "id")
    private Coupon coupon;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<CartItems> cartItems;

    public OrderDto getOrderDto() {

        return OrderDto.builder()
                .id(id)
                .orderDescription(orderDescription)
                .date(date)
                .amount(amount)
                .address(address)
                .totalAmount(totalAmount)
                .discount(discount)
                .payment(payment)
                .orderStatus(orderStatus)
                .trackingId(trackingId)
                .userName(user != null ? user.getName() : null)
                .couponName(coupon != null ? coupon.getName() : null)
                .build();

    }

    public Long getId() {
        return id;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public Date getDate() {
        return date;
    }

    public Long getAmount() {
        return amount;
    }

    public String getAddress() {
        return address;
    }

    public String getPayment() {
        return payment;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public Long getDiscount() {
        return discount;
    }

    public UUID getTrackingId() {
        return trackingId;
    }

    public User getUser() {
        return user;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public List<CartItems> getCartItems() {
        return cartItems;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public void setTrackingId(UUID trackingId) {
        this.trackingId = trackingId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public void setCartItems(List<CartItems> cartItems) {
        this.cartItems = cartItems;
    }
}
