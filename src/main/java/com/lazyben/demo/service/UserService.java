package com.lazyben.demo.service;

import com.lazyben.demo.exception.AuthenticationException;
import com.lazyben.demo.utils.JwtTokenUtil;
import com.lazyben.demo.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.lazyben.demo.constant.RedisKey.TOKEN_KEY;
import static com.lazyben.demo.constant.RedisKey.VISIT_USER_KEY;

@Service
@Slf4j
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public Map<String, String> login(String username, String password) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        var authenticate = authenticationManager.authenticate(authenticationToken);
        Optional.ofNullable(authenticate).orElseThrow(() -> new AuthenticationException("账号或密码错误！"));
        var userDetails = (UserDetails) authenticate.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        var token = RedisUtils.get(TOKEN_KEY + userDetails.getUsername());
        if (token == null || token.isEmpty()) {
            log.info(String.format("用户%s登陆系统，生成jwt token", userDetails.getUsername()));
            //如果token为空，则去创建一个新的token
            token = jwtTokenUtil.generateToken(userDetails);
            RedisUtils.set(TOKEN_KEY + userDetails.getUsername(), token, 3600L * 11);
        }
//        RedisUtils.sSetAndTime(VISIT_USER_KEY, 60 * 60 * 24, userDetails.getUsername() + System.currentTimeMillis());
        //加载前端菜单
        Map<String, String> map = new HashMap<>();
//        List<UserMenuVo> menus = null;
//        try {
//            menus = service.getUserMenus(userDetails.getUsername());
//        } catch (Exception e) {
//            e.printStackTrace();
//            R<Map<String, Object>> data = R.failed("获取用户菜单失败");
//            this.WriteJSON(request, response, data);
//            return;
//        }
        map.put("username", userDetails.getUsername());
//        map.put("auth", userDetails.getAuthorities().toString());
//        map.put("menus", menus);
        map.put("token", token);
        //装入token
//        ResponseStructure data = ResponseStructure.success(map);
        //输出
        return map;
    }

    public boolean checkLogin(String username, String password) {
        return true;
    }
}
