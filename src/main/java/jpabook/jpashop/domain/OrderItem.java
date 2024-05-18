package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;

    private int orderPrice;

    //==오더 아이템==//
    public static OrderItem createOrderItem( Item item, int count, int orderPrice) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem( item );
        orderItem.setCount( count );
        orderItem.setOrderPrice( orderPrice );
        item.removeStockQuantity(count);
        return orderItem;
    }
    //==비즈니스 로직==//
    public void cancel() {
        getItem().addStockQuantity(count);
    }
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
