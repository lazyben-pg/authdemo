package com.lazyben.demo.security;

import com.lazyben.demo.dao.RoleDao;
import com.lazyben.demo.dao.UserDao;
import com.lazyben.demo.exception.AuthenticationException;
import com.lazyben.demo.pojo.AuthUser;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserDao userDao;
    private final RoleDao roleDao;

    @Autowired
    public UserDetailServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userDo = userDao.selectByUsername(username);
        Optional.ofNullable(userDo).orElseThrow((() -> new AuthenticationException("账号或密码错误！")));
        var roles = roleDao.getRolesByUserName(userDo.getId());
        var authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return AuthUser.builder()
                .username(userDo.getUsername())
                .password(userDo.getPassword())
                .authorities(authorities)
                .build();
    }
}
