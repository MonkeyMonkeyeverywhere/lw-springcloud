package com.lw.cloud.controller;

import com.lw.cloud.annotation.ApiRateLimit;
import com.lw.cloud.base.ResponseData;
import com.lw.cloud.query.LoginQuery;
import com.lw.cloud.service.EnterpriseUserService;
import com.lw.cloud.util.JsonUtils;
import io.swagger.annotations.*;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
@Api(value = "用户控制器",tags = "用户接口")
public class EnterpriseUserController {

    @Autowired
    private EnterpriseUserService enterpriseUserService;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private Logger logger = LoggerFactory.getLogger(EnterpriseUserController.class);

    @ApiOperation(value = "用户登录",notes = "用户登录企业编号用户编号必填")
    @PostMapping("login")
    @ApiResponses({@ApiResponse(code = 403,message = "无权限访问")})
    @ResponseHeader(name = "myhead",description = "lw")
//    @ApiRateLimit(confKey = "open.api.defaultLimit")
    public ResponseData login(@Valid @RequestBody LoginQuery query){
        Long counter = stringRedisTemplate.opsForValue().increment("COUNTER", 1);
        Assert.notNull(query,"参数不能为空" );
        String token = "";
        RLock lock = redissonClient.getLock("REDISSON:LOCK");
        try {
            lock.lock();
            logger.info("Request Thread - " + counter + " locked and begun...");
            token = enterpriseUserService.login(query.getEid(), query.getUid());
//            Thread.sleep(5000); // 5 sec
            RTopic<String> topic = redissonClient.getTopic("REDISSON:LOGIN:MSG");
            topic.publish(query.getUid()+"登陆成功！");
            logger.info("Request Thread - " + counter + " ended successfully...");
        }catch (Exception e) {
            logger.error("log error");
        } finally {
            lock.unlock();
            logger.info("Request Thread - " + counter + " unlocked...");
        }
        return ResponseData.ok(token);
    }

    @GetMapping("redissonTest")
    @ApiOperation(value = "测试redisson",notes = "测试redisson")
    public String redissonTest(){
        RMap<Object, Object> test = redissonClient.getMap("test");
        test.addAndGet("key1", 1);
        test = redissonClient.getMap("test");
        return JsonUtils.toString(test);
    }

}
