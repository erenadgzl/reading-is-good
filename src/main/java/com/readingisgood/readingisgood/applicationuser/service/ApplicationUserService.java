package com.readingisgood.readingisgood.applicationuser.service;

import com.readingisgood.readingisgood.applicationuser.model.ApplicationUserDto;
import com.readingisgood.readingisgood.applicationuser.model.ApplicationUserResponse;
import com.readingisgood.readingisgood.applicationuser.entity.ApplicationUser;
import com.readingisgood.readingisgood.applicationuser.entity.Role;
import com.readingisgood.readingisgood.applicationuser.repository.ApplicationUserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.PostLoad;

@Service
public class ApplicationUserService {

    private ApplicationUserRepository applicationUserRepository;

    public ApplicationUserService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
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

    public ApplicationUserResponse applicationUserConvertToApplicationUserResponse(ApplicationUser applicationUser){
        ApplicationUserResponse userResponse = new ApplicationUserResponse();
        userResponse.setId(applicationUser.getId());
        userResponse.setName(applicationUser.getName());
        userResponse.setSurname(applicationUser.getSurname());
        userResponse.setUsername(applicationUser.getUsername());
        userResponse.setEmail(applicationUser.getEmail());
        userResponse.setAddress(applicationUser.getAddress());
        return userResponse;
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
        applicationUser.setPassword("Admin123!");
        applicationUser.setRole(Role.ROLE_ADMIN);
        applicationUser.setUsername("admintest");
        applicationUserRepository.save(applicationUser);
    }
}
