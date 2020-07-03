package com.example.demo.common.entity.drools;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName RuleResult
 * @Description
 * @Author wuhengzhen
 * @Date 2020-07-03 11:29
 * @Version 1.0
 */
@Data
@ToString
public class RuleResult {
    private boolean postCodeResult = false;

    public boolean isPostCodeResult() {
        return postCodeResult;
    }

    public void setPostCodeResult(boolean postCodeResult) {
        this.postCodeResult = postCodeResult;
    }
}
