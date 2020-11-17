package com.readingisgood.readingisgood.applicationuser.entity;

import com.readingisgood.readingisgood.base.entity.Address;
import com.readingisgood.readingisgood.base.entity.BaseEntity;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Audited
@Data
@Table(name = "USERS")
@Where(clause = "status <> 'DELETED'")
public class ApplicationUser extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPLICATION_USER_GENERATOR")
    @SequenceGenerator(name = "APPLICATION_USER_GENERATOR", sequenceName = "APPLICATION_USER_SEQ")
    private Long id;

    @NotNull(message = "İsminizi giriniz")
    @Size(min = 2,max = 50)
    @Column(name = "NAME",nullable = false)
    private String name;

    @NotNull(message = "Soyisminizi giriniz")
    @Size(min = 2,max = 50)
    @Column(name = "SURNAME",nullable = false)
    private String surname;

    @Size(min = 6,max = 50, message = "Kullanıcı adı 6-50 karakter olmalı.")
    @NotNull(message = "Kullanıcı adı boş bırakılamaz")
    @Column(name = "USERNAME" , nullable = false)
    private String username;

    @NotNull(message = "Email boş bırakılamaz")
    @Email(message = "Email adresinizi yanlış girdiniz")
    @Column(name = "EMAIL" , nullable = false)
    private String email;

    @NotNull(message = "Şifre giriniz")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$" ,
            message = "Şifreniz en az 8 karakter, 1 büyük harf, 1 küçük harf, 1 rakam ve 1 özel karakter (# ? ! @ $ % ^ & * -) içermelidir.")
    @Column(name = "PASSWORD")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private Role role;

    @Embedded
    private Address address;
}
