package com.example.demo.dao.mapper.base;

import com.example.demo.dao.entity.system.SUser;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA
 *
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-06 13:44
 */
@Repository
public interface UserBaseMapper {
    /**
     * 根据主键查询
     *
     * @param id
     * @return
     */
    SUser selectByPrimaryKey(Integer id);

    /**
     * 插入（匹配有值的字段）
     *
     * @param record UserDO
     * @return int
     */
    int insertSelective(SUser record);

    /**
     * 更新
     *
     * @param record
     * @return
     */

    int updateByPrimaryKeySelective(SUser record);

    /**
     * 根据主键删除
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);
}
