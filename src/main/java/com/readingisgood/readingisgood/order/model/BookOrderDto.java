package com.readingisgood.readingisgood.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookOrderDto implements Serializable {

    @NotNull
    @Min(0)
    private Long id;

    @NotNull
    @Min(0)
    private Integer quantity;
}
