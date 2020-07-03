package com.example.demo.web.controller.drools;

import com.example.demo.biz.service.drools.DroolsEngineService;
import com.example.demo.common.entity.Result;
import com.example.demo.common.entity.drools.RuleResult;
import com.example.demo.dao.entity.drools.QueryParam;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName DroolsEngineController
 * @Description Drools规则引擎Controller（简单示例）
 * @Author wuhengzhen
 * @Date 2020-07-03 10:27
 * @Version 1.0
 */
@RestController
@RequestMapping("/rule")
@Slf4j
public class DroolsEngineController {

    @Autowired
    private KieSession kieSession;

    @Autowired
    private DroolsEngineService droolsEngineService;


    /**
     * .drl文件中，salience 的值越大，越优先执行；
     * 规则流程：如果paramId不为null，参数标识是+号，执行添加规则，-号，执行移除规则操作。
     *
     * @return
     */
    @RequestMapping("/param")
    public Result droolsParam() {
        QueryParam queryParam1 = new QueryParam();
        queryParam1.setParamId("1");
        queryParam1.setParamSign("+");
        // QueryParam queryParam2 = new QueryParam();
        // queryParam2.setParamId("4");
        // queryParam2.setParamSign("+");
        QueryParam queryParam3 = new QueryParam();
        queryParam3.setParamId("2");
        queryParam3.setParamSign("-");
        // 入参
        kieSession.insert(queryParam1);
        kieSession.insert(queryParam3);
        kieSession.insert(this.droolsEngineService);

        // 返参
        RuleResult resultParam = new RuleResult();
        kieSession.insert(resultParam);
        kieSession.fireAllRules();
        return Result.wrapSuccessfulResult(resultParam);
    }

}
