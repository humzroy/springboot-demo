package com.example.demo.dao.mapper.business;

import com.example.demo.dao.entity.system.SUser;
import com.example.demo.dao.mapper.base.UserBaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wuhengzhen
 * @date 2019/11/06
 */
@Repository
public interface UserMapper extends UserBaseMapper {

    String getPassword(@Param("username") String username);

    SUser selectByUserName(@Param("username") String username);

    /**
     * 根据用户code更新盐
     *
     * @param userCode
     * @param salt
     * @return
     */
    int updateSaltByUserCode(@Param("userCode") String userCode, @Param("salt") String salt);
}