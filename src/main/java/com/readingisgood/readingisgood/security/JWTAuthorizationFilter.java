package com.readingisgood.readingisgood.security;

import com.readingisgood.readingisgood.account.service.UserDetailServiceImpl;
import com.readingisgood.readingisgood.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.readingisgood.readingisgood.security.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private UserDetailServiceImpl applicationUserService;
    private JwtUtil jwtUtil;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailServiceImpl applicationUserService, JwtUtil jwtUtil) {
        super(authenticationManager);

        this.applicationUserService = applicationUserService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = jwtUtil.getUserFromAccessToken(token);

            UserDetails userDetails = this.applicationUserService.loadUserByUsername(user);

            if (userDetails != null) {
                return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                        null,
                        userDetails.getAuthorities());
            }
            return null;
        }
        return null;
    }
}
