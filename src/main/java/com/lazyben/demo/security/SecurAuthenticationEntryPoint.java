package com.lazyben.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazyben.demo.constant.BizCode;
import com.lazyben.demo.pojo.ErrorResponse;
import com.lazyben.demo.exception.ServiceException;
import com.lazyben.demo.utils.WebUtil;
import lombok.var;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        var respBody = ErrorResponse.builder()
                .bizCode(BizCode.AUTHENTICATION_FAILED)
                .errorType(ServiceException.ErrorType.CLIENT)
                .msg("未成功认证！请检查token和账户密码是否正确！")
                .build();
        if (authException instanceof BadCredentialsException) {
            respBody.setMsg("账号或密码错误！");
            WebUtil.render(response, new ObjectMapper().writeValueAsString(respBody), HttpStatus.UNAUTHORIZED.value());
        }
        WebUtil.render(response, new ObjectMapper().writeValueAsString(respBody), HttpStatus.UNAUTHORIZED.value());
    }
}
