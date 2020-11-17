package com.readingisgood.readingisgood.book.model;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class BookDto implements Serializable {

    @Size(min = 2,max = 50, message = "İsim 2-50 karakter olmalı.")
    @NotNull(message = "İsim giriniz")
    private String name;

    @Size(min = 2,max = 100, message = "Yazar ismi 2-100 karakter olmalı.")
    @NotNull(message = "Yazar ismi giriniz")
    private String author;

    @DecimalMin("0.0")
    @NotNull
    private BigDecimal price;

    @Min(0)
    @NotNull
    private Long stock;
}
