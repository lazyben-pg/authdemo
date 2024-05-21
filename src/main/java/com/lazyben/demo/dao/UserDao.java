package com.lazyben.demo.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lazyben.demo.dao.mapper.UserMapper;
import com.lazyben.demo.pojo.UserDo;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    private final UserMapper userMapper;

    @Autowired
    public UserDao(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserDo selectByUsername(final String username) {
        var userDoQueryWrapper = new QueryWrapper<UserDo>().eq("username", username);
        return userMapper.selectOne(userDoQueryWrapper);
    }
}
