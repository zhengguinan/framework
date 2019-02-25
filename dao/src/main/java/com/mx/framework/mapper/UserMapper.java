package com.mx.framework.mapper;

import com.mx.framework.base.mapper.MyMapper;
import com.mx.framework.business.UserRolePer;
import com.mx.framework.po.User;
import com.mx.framework.po.UserPermission;
import com.mx.framework.po.UserRole;
import com.mx.framework.vo.UserRoleVo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : ShangGuanMingPeng
 * Description : 
 * Date :Create in
 * Modified By :
 */
@Mapper
@Repository
public interface UserMapper extends MyMapper<User> {

    @Select("SELECT user.id as userId, user.`name` as userName,user.pwd as password,user_role.role_name as userRoleName,user_permission.permission as permissionName from `user` \n" +
            "LEFT JOIN user_role ON `user`.id = user_role.user_id" +
            "LEFT JOIN user_permission ON user_role.id = user_permission.role_id" +
            "where `user`.name=#{name}")
    UserRolePer queryRolePer(@Param("name") String name);

    @Select("SELECT u.id,u.`name` from `user` as u where u.id = #{userId}")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(property = "userRoles",javaType = List.class,column = "id",
            many = @Many(select = "com.mx.framework.mapper.UserMapper.queryRoleByUserId"))
    })
    UserRoleVo queryUserRole(@Param("userId") Integer userId);

    @Select("SELECT * FROM permission p JOIN permission_role pr on p.pid = pr.pid WHERE pr.rid = #{rid}")
    List<UserPermission> queryByRoleId(Long rid);

    @Select("SELECT * FROM role r JOIN user_role ur on r.rid = ur.rid WHERE ur.uid = #{userId}")
    @Results({
            @Result(column = "rid",property = "rid"),
            @Result(property = "permissions",javaType = List.class,column = "rid",
            many = @Many(select = "com.mx.framework.mapper.UserMapper.queryByRoleId"))
    })
    List<UserRole> queryRoleByUserId(int userId);

    @Select("SELECT * FROM `user`")
    @Results({
            @Result(column = "id",property = "id"),
            @Result(property = "userRoles",javaType = List.class,column = "id",
            many = @Many(select = "com.mx.framework.mapper.UserMapper.queryRoleByUserId"))
    })
    User findByUserName(String username);
}
