package com.readingisgood.readingisgood.applicationuser.service;

import com.readingisgood.readingisgood.applicationuser.model.ApplicationUserDto;
import com.readingisgood.readingisgood.applicationuser.model.ApplicationUserResponse;
import com.readingisgood.readingisgood.applicationuser.entity.ApplicationUser;
import com.readingisgood.readingisgood.applicationuser.entity.Role;
import com.readingisgood.readingisgood.applicationuser.repository.ApplicationUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ApplicationUserService {

    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApplicationUserService(ApplicationUserRepository applicationUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public ApplicationUser save(ApplicationUserDto applicationUserDto){
        ApplicationUser applicationUser = userDtoConvertToApplicationUser(applicationUserDto);
        return applicationUserRepository.save(applicationUser);
    }

    public ApplicationUser findByUsername(String username){
        return applicationUserRepository.findByUsername(username);
    }

    public ApplicationUser findByEmail(String email){
        return applicationUserRepository.findByEmail(email);
    }

    private ApplicationUser userDtoConvertToApplicationUser(ApplicationUserDto applicationUserDto) {
        ApplicationUser user = new ApplicationUser();
        user.setName(applicationUserDto.getName());
        user.setSurname(applicationUserDto.getSurname());
        user.setPassword(applicationUserDto.getPassword());
        user.setUsername(applicationUserDto.getUsername());
        user.setPassword(applicationUserDto.getPassword());
        user.setEmail(applicationUserDto.getEmail());
        user.setRole(Role.ROLE_USER);
        user.setAddress(applicationUserDto.getAddress());
        return user;
    }

    public ApplicationUser findByEmailOrUsername(String user) {
        return applicationUserRepository.findByEmailOrUsername(user);
    }

    @PostConstruct
    private void initAdminUser(){
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setName("AdminTest");
        applicationUser.setSurname("Admin");
        applicationUser.setEmail("admin@gmail.com");
        applicationUser.setPassword(bCryptPasswordEncoder.encode("Admin123!"));
        applicationUser.setRole(Role.ROLE_ADMIN);
        applicationUser.setUsername("admintest");
        applicationUserRepository.save(applicationUser);
    }
}
