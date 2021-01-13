package com.example.demo.biz.service;

import com.example.demo.common.constant.ConstantMongoDB;
import com.example.demo.common.util.ThreadPoolUtils;
import com.example.demo.web.DemoWebApplication;
import com.mongodb.client.result.DeleteResult;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName DemoServiceTest
 * @Description
 * @Author wuhengzhen
 * @Date 2019-11-06 17:29
 */

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {DemoWebApplication.class})
public class DemoServiceTest {

    @Autowired
    private DemoService demoService;

    @Autowired
    StringEncryptor encryptor;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ThreadPoolUtils threadPoolUtils;

    @Test
    public void test() {
        System.out.println(demoService.test(1));
    }

    @Test
    public void testLog() {
        log.trace("=====trace=====");
        log.debug("=====debug=====");
        log.info("=====这是info级别=====");
        log.warn("=====warn=====");
        log.error("=====error=====");
    }

    @Test
    public void testTransaction() {
        demoService.testTransaction();
    }

    // @Test
    // public void testEncryptor() {
    //     String str1 = "root";
    //     System.out.println("root加密后: "+encryptor.encrypt(str1));
    //     String str2 = "a123456";
    //     System.out.println("a123456加密后: "+encryptor.encrypt(str2));
    //     String str3 = "47.104.9.23:2171";
    //     System.out.println("47.104.9.23:2171加密后: "+encryptor.encrypt(str3));
    //     String str4 = "47.104.9.23:6000,47.104.9.23:6001,47.104.9.23:6002,47.104.9.23:6003,47.104.9.23:6004,47.104.9.23:6005";
    //     System.out.println("加密后: "+encryptor.encrypt(str4));
    //
    // }

    /**
     * @description 测试MongoDB
     * @author wuhengzhen
     * @date 2020/6/23 11:05
     **/
    @Test
    public void testMongoDB() {

        // **************** query ****************
        // Query query = new Query();
        //
        // Map map = mongoTemplate.findOne(query, Map.class, ConstantMongoDB.MONGO_COLLECTION_MY_TEST);
        // System.out.println("查询结果：" + map);
        // **************** query ****************


        // **************** insert ****************
        // Map<String, Object> insertMap = new HashMap<>();
        // insertMap.put("name", "jerry");
        // insertMap.put("age", 30);
        // insertMap.put("email", "jerry@test.com");
        // insertMap.put("address", "JiNan");
        //
        // Map<String, Object> insert = mongoTemplate.insert(insertMap, ConstantMongoDB.MONGO_COLLECTION_MY_TEST);
        // System.out.println("新增记录：" + insert.toString());
        // **************** insert ****************


        // **************** update ****************
        // Query updateQuery = new Query();
        // updateQuery.addCriteria(Criteria.where("name").is("tom"));
        // Update update = new Update();
        // update.set("age", 28);
        // //查询到的全部更新
        // // UpdateResult upsert = mongoTemplate.updateMulti(updateQuery, update, ConstantMongoDB.MONGO_COLLECTION_MY_TEST);
        // //查询更新第一条
        // // UpdateResult upsert = mongoTemplate.updateFirst(updateQuery, update, ConstantMongoDB.MONGO_COLLECTION_MY_TEST);
        // //有则更新，没有则新增
        // UpdateResult updateResult = mongoTemplate.upsert(updateQuery, update, ConstantMongoDB.MONGO_COLLECTION_MY_TEST);
        // System.out.println("更新数：" + updateResult.getModifiedCount());
        // **************** update ****************


        // **************** delete ****************
        // Query deleteQuery = new Query();
        // deleteQuery.addCriteria(Criteria.where("name").is("jerry"));
        // DeleteResult deleteResult = mongoTemplate.remove(deleteQuery, ConstantMongoDB.MONGO_COLLECTION_MY_TEST);
        // System.out.println("删除数：" + deleteResult.getDeletedCount());
        // **************** delete ****************


        // ************ test ************
        Query query = new Query();
        query.addCriteria(Criteria.where("tids").in("sa"));
        query.fields().include("name");
        Map map = mongoTemplate.findOne(query, Map.class, ConstantMongoDB.MONGO_COLLECTION_MY_COLLECTION);
        System.out.println(map);

        Update update = new Update();
        update.set("name", "tom");

        System.out.println(update.getUpdateObject().isEmpty());

        mongoTemplate.updateMulti(query, update, ConstantMongoDB.MONGO_COLLECTION_MY_COLLECTION);

    }

    @Test
    public void testThreadPool(){
        threadPoolUtils.execute(()->{
            System.out.println("doing you want do!");
        });
    }

}
