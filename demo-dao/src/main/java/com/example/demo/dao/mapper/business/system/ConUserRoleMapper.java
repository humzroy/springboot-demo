package com.example.demo.dao.mapper.business.system;

import com.example.demo.dao.mapper.base.system.ConUserRoleBaseMapper;

import java.util.Collection;

/**
 * @Description
 * @Author wuhengzhen
 * @Date 2019-12-25 13:28
 */
public interface ConUserRoleMapper extends ConUserRoleBaseMapper {
    /**
     * 根据userId获取所有roleId
     *
     * @param userId userId
     * @return roleIds
     */
    Collection<String> selectRoleNamesByUserId(Integer userId);
}
