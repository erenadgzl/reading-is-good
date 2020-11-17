package com.readingisgood.readingisgood.book.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class StockDto implements Serializable {

    @NotNull
    @Min(0)
    private Integer quantity;

    @Min(0)
    private Long bookId;
}
