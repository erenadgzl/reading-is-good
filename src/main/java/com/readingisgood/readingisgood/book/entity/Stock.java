package com.readingisgood.readingisgood.book.entity;

import com.readingisgood.readingisgood.base.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Audited
@Data
@Table(name = "STOCKS")
@Where(clause = "status <> 'DELETED'")
public class Stock extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STOCK_GENERATOR")
    @SequenceGenerator(name = "STOCK_GENERATOR", sequenceName = "STOCK_SEQ")
    private Long id;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;
}
