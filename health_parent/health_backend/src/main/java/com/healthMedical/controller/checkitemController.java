package com.healthMedical.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.healthMedical.constant.MessageConstant;
import com.healthMedical.entity.PageResult;
import com.healthMedical.entity.QueryPageBean;
import com.healthMedical.entity.Result;
import com.healthMedical.pojo.CheckItem;
import com.healthMedical.service.checkitemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.PathParam;
import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class checkitemController {
    //dubbo 远程注入
    @Reference
    private checkitemService checkitemService;

    // 新增检查项
    @RequestMapping("/add")
    //默认情况下，springMVC是不能不能封装json数据的，我们就需要添加注解以下注解
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkitemService.add(checkItem);
        } catch (Exception e) {
            //服务调用失败
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }

        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult page = checkitemService.findPage(queryPageBean);
        return page;
    }

    //删除检查项
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            checkitemService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }

        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }


    @RequestMapping("/findCheckitemById")
    public Result edit(Integer id) {
        try {
            CheckItem checkItem = checkitemService.findCheckitemById(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    //编辑检查项
    @RequestMapping("/edit")
    public Result delete(@RequestBody CheckItem checkItem) {
        try {
            checkitemService.edit(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }

        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/findAll")
    public Result findAll() {
        try {
            List<CheckItem> checkItem = checkitemService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
}
