package com.lazyben.demo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazyben.demo.constant.BizCode;
import com.lazyben.demo.exception.AuthenticationException;
import com.lazyben.demo.exception.ErrorResponse;
import com.lazyben.demo.exception.ServiceException;
import com.lazyben.demo.utils.JwtTokenUtil;
import com.lazyben.demo.utils.WebUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurOncePerRequestFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final String header = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String headerToken = request.getHeader(header);
        if (headerToken == null || headerToken.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }
        String username = null;
        ErrorResponse respBody = ErrorResponse.builder().bizCode(BizCode.AUTHENTICATION_FAILED)
                                              .errorType(ServiceException.ErrorType.CLIENT)
                                              .build();
        String token = headerToken.replace("Bearer", "").trim();
        try {
            username = jwtTokenUtil.getUsernameFromToken(token);
        } catch (ExpiredJwtException e) {
            respBody.setMsg("token过期，请重新登陆！");
            WebUtil.render(response, new ObjectMapper().writeValueAsString(respBody), HttpServletResponse.SC_UNAUTHORIZED);
        } catch (SignatureException e) {
            respBody.setMsg("token非法，请重新登陆！");
            WebUtil.render(response, new ObjectMapper().writeValueAsString(respBody), HttpServletResponse.SC_UNAUTHORIZED);
        }
        //判断用户不为空，且SecurityContextHolder授权信息还是空的
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //通过用户信息得到UserDetails
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            //验证令牌有效性
            boolean validata = false;
            try {
                validata = jwtTokenUtil.validateToken(token, userDetails);
            } catch (Exception e) {
                respBody.setMsg("token非法，请重新登陆！");
                WebUtil.render(response, new ObjectMapper().writeValueAsString(respBody), HttpServletResponse.SC_UNAUTHORIZED);
            }
            if (validata) {
                // 将用户信息存入 authentication，方便后续校验
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 将 authentication 存入 ThreadLocal，方便后续获取用户信息
                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(request, response);
            }
        }
    }
}
