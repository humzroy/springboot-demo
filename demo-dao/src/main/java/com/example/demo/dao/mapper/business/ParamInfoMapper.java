package com.example.demo.dao.mapper.business;

import com.example.demo.dao.entity.drools.ParamInfo;
import com.example.demo.dao.mapper.base.drools.ParamInfoBaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author wuhengzhen
 * @Date 2020-07-03 10:47
 */
@Repository
public interface ParamInfoMapper extends ParamInfoBaseMapper {

    void insertParamRecord(ParamInfo paramInfo);

}
