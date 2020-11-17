package com.readingisgood.readingisgood.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse implements Serializable {

    private Long id;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private String bookName;
    private String bookAuthor;
}
