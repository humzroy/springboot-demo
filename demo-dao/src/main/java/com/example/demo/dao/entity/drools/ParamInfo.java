package com.example.demo.dao.entity.drools;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @ClassName ParamInfo
 * @Description
 * @Author wuhengzhen
 * @Date 2020-07-03 10:43
 * @Version 1.0
 */
@Data
@ToString
@TableName("re_param_info")
public class ParamInfo {

    @TableId("id")
    private String id;
    private String paramSign;
    private Date createTime;
    private Date updateTime;

}
