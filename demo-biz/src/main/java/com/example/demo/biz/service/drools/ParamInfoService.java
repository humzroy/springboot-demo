package com.example.demo.biz.service.drools;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.common.datasource.DataSource;
import com.example.demo.dao.entity.drools.ParamInfo;

/**
 * @Description 针对re_param_info表的service
 * @Author wuhengzhen
 * @Date 2020-07-03 11:43
 */
public interface ParamInfoService extends IService<ParamInfo> {
    /**
     * 根据id查询
     *
     * @param paramId
     * @return
     */
    @DataSource
    ParamInfo selectById(String paramId);

    /**
     * 新增一条记录
     *
     * @param paramInfo
     */
    @DataSource
    void insertParam(ParamInfo paramInfo);
}
