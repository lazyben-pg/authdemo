package com.lazyben.demo.dao;

import com.lazyben.demo.dao.mapper.RoleMapper;
import com.lazyben.demo.pojo.RoleDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDao {
    private final RoleMapper roleMapper;

    @Autowired
    public RoleDao(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public List<RoleDo> getRolesByUserName(Long id) {
        return roleMapper.getRolesByUserId(id);
    }
}
