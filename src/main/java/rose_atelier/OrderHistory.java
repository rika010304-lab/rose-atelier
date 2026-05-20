package rose_atelier;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String variety;

    private int quantity;

    private int price;

    private int subtotal;

    private LocalDateTime orderedAt;

    public OrderHistory() {
    }

    public OrderHistory(
            String variety,
            int quantity,
            int price,
            int subtotal,
            LocalDateTime orderedAt
    ) {
        this.variety = variety;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
        this.orderedAt = orderedAt;
    }

    public String getVariety() {
        return variety;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public LocalDateTime getOrderedAt() {
        return orderedAt;
    }
}