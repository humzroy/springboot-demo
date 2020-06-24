package com.example.demo.biz.service.impl.system;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.biz.service.system.IConUserRoleService;
import com.example.demo.dao.entity.system.ConUserRole;
import com.example.demo.dao.mapper.business.system.ConUserRoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wuhengzhen
 * @since 2019-12-17
 */
@Service
public class ConUserRoleServiceImpl extends ServiceImpl<ConUserRoleMapper, ConUserRole> implements IConUserRoleService {

    @Resource
    private ConUserRoleMapper userRoleMapper;

    @Override
    public Collection<String> selectRoleNamesByUserId(Integer userId) {
        return userRoleMapper.selectRoleNamesByUserId(userId);
    }
}
