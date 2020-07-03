package com.example.demo.biz.service.impl.drools;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.biz.service.drools.ParamInfoService;
import com.example.demo.dao.entity.drools.ParamInfo;
import com.example.demo.dao.mapper.business.ParamInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @ClassName ParamInfoServiceImpl
 * @Description 针对re_param_info表的service实现
 * @Author wuhengzhen
 * @Date 2020-07-03 11:45
 * @Version 1.0
 */
@Service
@Slf4j
public class ParamInfoServiceImpl extends ServiceImpl<ParamInfoMapper, ParamInfo> implements ParamInfoService {

    @Resource
    private ParamInfoMapper paramInfoMapper;

    /**
     * 根据id查询
     *
     * @param paramId
     * @return
     */
    @Override
    public ParamInfo selectById(String paramId) {
        ParamInfo paramInfo = paramInfoMapper.selectById(paramId);
        if (Objects.nonNull(paramInfo)) {
            log.info("ParamInfoServiceImpl-Sign：{}", paramInfo.getParamSign());
        } else {
            log.info("ParamInfoServiceImpl：{},paramId:{}", "未查询到数据", paramId);
        }
        return paramInfo;
    }

    /**
     * 新增一条记录
     *
     * @param paramInfo
     */
    @Override
    public void insertParam(ParamInfo paramInfo) {
        paramInfoMapper.insertParamRecord(paramInfo);
    }
}
