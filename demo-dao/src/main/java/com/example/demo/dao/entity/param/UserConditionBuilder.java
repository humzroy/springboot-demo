package com.example.demo.dao.entity.param;

import lombok.Builder;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName UserDO
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-06 14:12
 */
@Builder
public class UserConditionBuilder {

    /**
     * 自增ID
     */
    private Integer id;

    /**
     * id的List条件
     */
    private List<Integer> idList;

    /**
     * 用户昵称
     */
    private String userNick;

}
