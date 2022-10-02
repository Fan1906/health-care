package com.healthMedical.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.healthMedical.constant.MessageConstant;
import com.healthMedical.constant.RedisConstant;
import com.healthMedical.entity.PageResult;
import com.healthMedical.entity.QueryPageBean;
import com.healthMedical.entity.Result;
import com.healthMedical.pojo.Setmeal;
import com.healthMedical.service.setMealService;
import com.healthMedical.utils.QiniuUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class setMealcontroller {
    @Reference
    private setMealService service;
    //JedisPool
    @Resource
    private JedisPool jedisPool;

    //文件上传
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        System.out.println(imgFile);
        //获取原始文件名
        String oldName=imgFile.getOriginalFilename();
        int index=oldName.lastIndexOf(".");
        String exteneion= oldName.substring(index);
        //上传文件名，防止文件名重复
        String fileName= UUID.randomUUID().toString()+exteneion;
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //图片上传成功后，添加到redis中
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);

        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
    }

    //新增套餐
    @RequestMapping("/add")
    public Result add(Integer [] checkgroupsIds, @RequestBody Setmeal setmeal){
        try{
            service.add(setmeal,checkgroupsIds);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findAll(@RequestBody QueryPageBean queryPageBean){
        try{
            PageResult page= service.findPage(queryPageBean);
            return page;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //根据id删除套餐
    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        try{
            service.deleteById(id);
            return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }

    /**
     * 根据ID查询套餐信息
     * @param id
     * @return
     */
    @RequestMapping("/findSetMealById")
    public Result findSetMealByid(Integer id){
        try{
            Setmeal setmealt= service.findSetMealById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmealt);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/selectCheckGroupBySetMealId")
    public Result selectCheckGroupBySetMealId(Integer id){
        try{
            List<Integer> list= service.selectCheckGroupBySetMealId(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }


    @RequestMapping("/update")
    public Result update(@RequestBody Setmeal setmeal,Integer [] ids){
        try{
            System.out.println(setmeal.toString());
            System.out.println(ids);
            service.update(setmeal,ids);
            return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_SETMEAL_FAIL);
        }
    }
}
