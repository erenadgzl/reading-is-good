package com.readingisgood.readingisgood.applicationuser.repository;

import com.readingisgood.readingisgood.applicationuser.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUsername(String username);

    ApplicationUser findByEmail(String email);

    @Query("SELECT au from ApplicationUser au WHERE (au.email = :emailOrUsername OR au.username = :emailOrUsername) AND au.status = 'ACTIVE'")
    ApplicationUser findByEmailOrUsername(String emailOrUsername);
}
