package com.example.demo.biz.service.drools;

import com.example.demo.dao.entity.drools.QueryParam;

/**
 * @Description Drools规则引擎service（简单示例）
 * @Author wuhengzhen
 * @Date 2020-07-03 10:36
 */
public interface DroolsEngineService {

    /**
     * 执行规则
     *
     * @param param
     */
    void executeAddRule(QueryParam param);

    /**
     * 删除规则
     *
     * @param param
     */
    void executeRemoveRule(QueryParam param);

}
