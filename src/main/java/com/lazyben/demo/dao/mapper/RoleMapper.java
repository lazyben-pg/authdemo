package com.lazyben.demo.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lazyben.demo.pojo.RoleDo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper extends BaseMapper<RoleDo> {
    @Select("select r.id,r.name,r.remark,r.created_at,r.updated_at,r.status from user_role ur left join role r on ur.role_id=r.id where ur.user_id=#{id}")
    List<RoleDo> getRolesByUserId(long id);
}
