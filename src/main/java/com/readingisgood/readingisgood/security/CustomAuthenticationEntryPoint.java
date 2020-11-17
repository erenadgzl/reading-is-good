package com.readingisgood.readingisgood.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingisgood.readingisgood.base.entity.CustomResponseStatus;
import com.readingisgood.readingisgood.base.entity.GenericErrorResponse;
import com.readingisgood.readingisgood.base.entity.GenericResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req,
                         HttpServletResponse res,
                         AuthenticationException authException)
            throws IOException, ServletException {

        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(401);

        List<String> details = new ArrayList<>();
        details.add("Kullanıcı bilgisi geçersiz!");
        GenericErrorResponse error = new GenericErrorResponse("Kullanıcı bilgisi geçersiz!", details);
        GenericResponse apiResponse = new GenericResponse();
        apiResponse.setStatus(CustomResponseStatus.ERROR.getValue());
        apiResponse.setError(error);

        ObjectMapper mapper = new ObjectMapper();
        res.getWriter().write(mapper.writeValueAsString(apiResponse));
    }

}
