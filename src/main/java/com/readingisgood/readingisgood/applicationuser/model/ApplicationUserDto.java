package com.readingisgood.readingisgood.applicationuser.model;

import com.readingisgood.readingisgood.base.entity.Address;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class ApplicationUserDto implements Serializable {

    @Size(min = 2,max = 50, message = "İsim 2-50 karakter olmalı.")
    @NotNull(message = "İsminizi giriniz")
    private String name;

    @Size(min = 2,max = 50, message = "Soyisim 2-50 karakter olmalı.")
    @NotNull(message = "Soyisminizi giriniz")
    private String surname;

    @NotNull(message = "Kullanıcı adı boş bırakılamaz")
    @Size(min = 6,max = 50, message = "Kullanıcı adı 6-50 karakter olmalı.")
    @Pattern(regexp="^[A-Za-z0-9]+", message = "Kullanıcı adı yalnızca ingilizce karakter ve rakam içerebilir.")
    private String username;

    @Email(message = "Email adresinizi yanlış girdiniz")
    @NotNull(message = "Email boş bırakılamaz")
    private String email;

    @NotNull(message = "Şifre giriniz")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$" ,
            message = "Şifreniz en az 8 karakter, 1 büyük harf, 1 küçük harf, 1 rakam ve 1 özel karakter (# ? ! @ $ % ^ & * -) içermelidir.")
    private String password;

    private Address address;
}
