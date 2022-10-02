package com.healthMedical.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.healthMedical.constant.MessageConstant;
import com.healthMedical.entity.PageResult;
import com.healthMedical.entity.QueryPageBean;
import com.healthMedical.entity.Result;
import com.healthMedical.pojo.CheckGroup;
import com.healthMedical.service.checkGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 检查组关管理
 */

@RestController
@RequestMapping("/checkgroup")
public class checkGroupControllrt {
    @Reference
    private checkGroupService checkGroupService;

    //新增检查组
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer [] checkitemIds){
        try{
            checkGroupService.add(checkGroup,checkitemIds);
            return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }
    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return checkGroupService.findPage(queryPageBean);
    }
    //查询某个检查项的值
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            CheckGroup checkGroup= checkGroupService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }

    //根据检查组id查询多个检查项ID
    @RequestMapping("/findCheckItemByCheckGroupId")
    public Result findCheckItemByCheckGroupId(Integer id){
        try{
            List<Integer> list= checkGroupService.findCheckItemByCheckGroupId(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    //编辑检查组
    @RequestMapping("/edit")
    public Result edit(Integer [] checkitemIds,@RequestBody CheckGroup checkGroup){
        try{
            checkGroupService.edit(checkitemIds,checkGroup);
            return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    //删除检查组
    @RequestMapping("/deleteById")
    public Result deleteById(Integer id){
        try{
            checkGroupService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        try{
            List<CheckGroup> list= checkGroupService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }
}
