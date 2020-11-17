package com.readingisgood.readingisgood.book.entity;

import com.readingisgood.readingisgood.base.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Audited
@Data
@Table(name = "BOOKS")
@Where(clause = "status <> 'DELETED'")
public class Book extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_GENERATOR")
    @SequenceGenerator(name = "BOOK_GENERATOR", sequenceName = "BOOK_SEQ")
    private Long id;

    @Column(name = "NAME",nullable = false)
    private String name;

    @Column(name = "AUTHOR",nullable = false)
    private String author;

    @Column(name = "PRICE",nullable = false)
    private BigDecimal price;

    @Column(name = "STOCK",nullable = false)
    private Long stock;
}
