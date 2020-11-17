package com.readingisgood.readingisgood.order.entity;

import com.readingisgood.readingisgood.base.entity.BaseEntity;
import com.readingisgood.readingisgood.book.entity.Book;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Audited
@Data
@Table(name = "ORDER_DETAILS")
@Where(clause = "status <> 'DELETED'")
public class OrderDetail extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_DETAIL_GENERATOR")
    @SequenceGenerator(name = "ORDER_DETAIL_GENERATOR", sequenceName = "ORDER_DETAIL_SEQ")
    private Long id;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Column(name = "TOTAL_PRICE", nullable = false)
    private BigDecimal totalPrice;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Book book;
}
