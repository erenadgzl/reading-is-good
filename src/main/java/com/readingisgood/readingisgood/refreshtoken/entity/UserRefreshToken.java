package com.readingisgood.readingisgood.refreshtoken.entity;

import com.readingisgood.readingisgood.applicationuser.entity.ApplicationUser;
import com.readingisgood.readingisgood.base.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USER_REFRESH_TOKENS")
@Where(clause = "status <> 'DELETED'")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class UserRefreshToken extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @NonNull
    @Column(nullable = false)
    private String token;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    private ApplicationUser user;
}
