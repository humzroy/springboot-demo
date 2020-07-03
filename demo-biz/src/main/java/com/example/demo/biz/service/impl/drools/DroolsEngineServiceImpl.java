package com.example.demo.biz.service.impl.drools;

import com.example.demo.biz.service.drools.DroolsEngineService;
import com.example.demo.biz.service.drools.ParamInfoService;
import com.example.demo.common.util.SpringUtils;
import com.example.demo.dao.entity.drools.ParamInfo;
import com.example.demo.dao.entity.drools.QueryParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @ClassName DroolsEngineServiceImpl
 * @Description Drools规则引擎service实现（简单示例）
 * @Author wuhengzhen
 * @Date 2020-07-03 10:36
 * @Version 1.0
 */
@Slf4j
@Service
public class DroolsEngineServiceImpl implements DroolsEngineService {

    /**
     * 执行新增记录规则
     *
     * @param param
     */
    @Override
    public void executeAddRule(QueryParam param) {
        log.info("参数数据:" + param.getParamId() + ";" + param.getParamSign());
        ParamInfo paramInfo = new ParamInfo();
        paramInfo.setId(param.getParamId());
        paramInfo.setParamSign(param.getParamSign());
        paramInfo.setCreateTime(new Date());
        paramInfo.setUpdateTime(new Date());
        ParamInfoService paramInfoService = SpringUtils.getBean(ParamInfoService.class);
        ParamInfo record = paramInfoService.selectById(paramInfo.getId());
        if (Objects.nonNull(record)) {
            log.info("已经存在记录！" + paramInfo.toString());
        } else {
            paramInfoService.insertParam(paramInfo);
        }
    }

    /**
     * 执行删除记录规则
     *
     * @param param
     */
    @Override
    public void executeRemoveRule(QueryParam param) {
        log.info("参数数据:" + param.getParamId() + ";" + param.getParamSign());
        ParamInfoService paramInfoService = SpringUtils.getBean(ParamInfoService.class);
        ParamInfo paramInfo = paramInfoService.selectById(param.getParamId());
        if (paramInfo != null) {
            paramInfoService.removeById(param.getParamId());
        }
    }

}
