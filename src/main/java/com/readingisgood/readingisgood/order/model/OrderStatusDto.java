package com.readingisgood.readingisgood.order.model;

import com.readingisgood.readingisgood.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusDto implements Serializable {
    @NotNull
    private OrderStatus orderStatus;
}
