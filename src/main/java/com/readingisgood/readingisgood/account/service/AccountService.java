package com.readingisgood.readingisgood.account.service;

import com.readingisgood.readingisgood.account.model.AccessToken;
import com.readingisgood.readingisgood.applicationuser.model.ApplicationUserDto;
import com.readingisgood.readingisgood.account.model.TokenPair;
import com.readingisgood.readingisgood.applicationuser.entity.ApplicationUser;
import com.readingisgood.readingisgood.applicationuser.service.ApplicationUserService;
import com.readingisgood.readingisgood.base.exception.ConflictException;
import com.readingisgood.readingisgood.base.exception.RecordNotFoundException;
import com.readingisgood.readingisgood.base.exception.UnAuthorizedException;
import com.readingisgood.readingisgood.refreshtoken.entity.UserRefreshToken;
import com.readingisgood.readingisgood.refreshtoken.repository.UserRefreshTokenRepository;
import com.readingisgood.readingisgood.security.model.UserLogin;
import com.readingisgood.readingisgood.utils.JwtUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.readingisgood.readingisgood.security.SecurityConstants.TOKEN_PREFIX;

@Service
public class AccountService {

    private ApplicationUserService applicationUserService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtUtil jwtUtil;
    private UserRefreshTokenRepository userRefreshTokenRepository;


    public AccountService(ApplicationUserService applicationUserService, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil, UserRefreshTokenRepository userRefreshTokenRepository) {
        this.applicationUserService = applicationUserService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
        this.userRefreshTokenRepository = userRefreshTokenRepository;
    }

    public TokenPair register(ApplicationUserDto applicationUserDto) {

        applicationUserDto.setPassword(bCryptPasswordEncoder.encode(applicationUserDto.getPassword()));

        if (applicationUserService.findByUsername(applicationUserDto.getUsername()) != null) {
            throw new ConflictException("Başarısız! Kullanıcı adı kullanılıyor!");
        }
        if (applicationUserService.findByEmail(applicationUserDto.getEmail()) != null) {
            throw new ConflictException("Başarısız! E-posta kullanılıyor!");
        }

        ApplicationUser applicationUser = applicationUserService.save(applicationUserDto);

        return doLoginUser(applicationUser);
    }

    public TokenPair loginUser(UserLogin userLogin) {
        ApplicationUser applicationUser = applicationUserService.findByEmailOrUsername(userLogin.getUsername());
        if(applicationUser == null){
            throw new RecordNotFoundException("Kullanıcı bulunamadı!");
        }
        if (bCryptPasswordEncoder.matches(userLogin.getPassword(), applicationUser.getPassword())) {
            return doLoginUser(applicationUser);
        }else {
            throw new UnAuthorizedException("Kullanıcı adı veya şifre yanlış!");
        }
    }

    public TokenPair doLoginUser(ApplicationUser user) {
        String jwt = jwtUtil.generateAccessToken(user.getUsername());
        String refreshToken = createRefreshToken(user);
        return new TokenPair(TOKEN_PREFIX + jwt, refreshToken);
    }

    private String createRefreshToken(ApplicationUser user) {
        String token = RandomStringUtils.randomAlphanumeric(128);
        userRefreshTokenRepository.save(new UserRefreshToken(token, user));
        return token;
    }

    public Optional<AccessToken> refreshAccessToken(String refreshToken) {
        Optional<UserRefreshToken> userRefreshTokenOptional = userRefreshTokenRepository.findByToken(refreshToken);

        if(userRefreshTokenOptional.isPresent()){
            UserRefreshToken userRefreshToken = userRefreshTokenOptional.get();
            return Optional.of(new AccessToken(jwtUtil.generateAccessToken(userRefreshToken.getUser().getUsername())));
        }else{
            throw new UnAuthorizedException("Refresh token geçersiz!");
        }
    }

    public void logoutUser(String refreshToken) {
        userRefreshTokenRepository.findByToken(refreshToken)
                .ifPresent(userRefreshTokenRepository::delete);
    }
}