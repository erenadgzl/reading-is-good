package com.readingisgood.readingisgood.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingisgood.readingisgood.base.entity.CustomResponseStatus;
import com.readingisgood.readingisgood.base.entity.GenericErrorResponse;
import com.readingisgood.readingisgood.base.entity.GenericResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(403);

        List<String> details = new ArrayList<>();
        details.add("Yetkiniz bulunmuyor!");
        GenericErrorResponse error = new GenericErrorResponse("Yetkiniz bulunmuyor!", details);
        GenericResponse apiResponse = new GenericResponse();
        apiResponse.setStatus(CustomResponseStatus.ERROR.getValue());
        apiResponse.setError(error);

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(apiResponse));
    }
}