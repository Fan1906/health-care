package com.healthMedical.jobs;

import com.healthMedical.constant.RedisConstant;
import com.healthMedical.utils.QiniuUtils;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Set;

public class deleteImageJob {
    @Resource
    private JedisPool jedisPool;
    public void deleteImg(){
        //每天早上一点执行
        //根据差值计算出 垃圾图片
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_DB_RESOURCES, RedisConstant.SETMEAL_PIC_RESOURCES);
        if(set!=null){
            for(String fileName:set){
                //删除图片
                QiniuUtils.deleteFileFromQiniu(fileName);
                //删除垃圾图片名
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            }
        }
    }
    //定时清除redis数据库里的，不需要持久化。数据已经存储在MySQL数据库
    // 当一个集合的所有成员都被移除后，Redis会自动移除该集合。因此，删除键的行为与手动从键中删除所有成员的行为相同。
    //
    // 此外，删除键应该比逐个删除成员快得多，因为它节省了大量的往返时间。
    public void clearRedis(){
        //每天早上两点执行
        jedisPool.getResource().del(RedisConstant.SETMEAL_PIC_DB_RESOURCES,RedisConstant.SETMEAL_PIC_RESOURCES);
        jedisPool.getResource().set(RedisConstant.SETMEAL_PIC_DB_RESOURCES,"init");
        jedisPool.getResource().set(RedisConstant.SETMEAL_PIC_RESOURCES,"init");
    }
}
