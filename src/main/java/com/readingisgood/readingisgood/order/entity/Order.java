package com.readingisgood.readingisgood.order.entity;

import com.readingisgood.readingisgood.applicationuser.entity.ApplicationUser;
import com.readingisgood.readingisgood.base.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Audited
@Data
@Table(name = "ORDERS")
@Where(clause = "status <> 'DELETED'")
public class Order extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_GENERATOR")
    @SequenceGenerator(name = "ORDER_GENERATOR", sequenceName = "ORDER_SEQ")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ORDER_STATUS", nullable = false)
    private OrderStatus orderStatus;

    @ManyToOne
    private ApplicationUser applicationUser;
}
