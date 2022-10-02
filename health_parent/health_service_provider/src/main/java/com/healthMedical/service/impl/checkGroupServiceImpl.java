package com.healthMedical.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.healthMedical.entity.PageResult;
import com.healthMedical.entity.QueryPageBean;
import com.healthMedical.mapper.checkGroupMapper;
import com.healthMedical.pojo.CheckGroup;
import com.healthMedical.service.checkGroupService;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

//对外暴露接口
@Service(interfaceClass = checkGroupService.class)
@Transactional
public class checkGroupServiceImpl implements checkGroupService {
    @Resource
    private checkGroupMapper checkGroupMapper;

    /**
     * 新增检查组，要与检查项关联
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //新增检查组
        checkGroupMapper.add(checkGroup);
        //完成检查组和检查项相关联
        Integer id = checkGroup.getId();
        if(checkitemIds!=null && checkitemIds.length>0){
           setCheckGroupAndCheckItem(id,checkitemIds);
        }
    }

    //分页查询
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page=checkGroupMapper.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupMapper.findById(id);
    }

    @Override
    public List<Integer> findCheckItemByCheckGroupId(Integer id) {
        return checkGroupMapper.findCheckItemByCheckGroupId(id);
    }

    // 编辑检查组信息，并关联检查项
    @Override
    public void edit(Integer[] checkitemIds, CheckGroup checkGroup) {
        //以后可以让前端做判断，增加、减少都要可以运行
        Integer id=checkGroup.getId();
        checkGroupMapper.editCheckGroup(checkGroup);
        //删除所有该检查组、检查项的关联关系
        checkGroupMapper.deleteGroupAndCheckItemById(id);
        //查询插入
        setCheckGroupAndCheckItem(id,checkitemIds);
    }

    @Override
    public void deleteById(Integer id) {
        //如果该检查组已经与套餐相关联，无法删除
        if(checkGroupMapper.findSetMealByCheckGroupId(id)>0){
            checkGroupMapper.deleteGroupAndCheckItemById(id);
            checkGroupMapper.deleteCheckGroupById(id);
        }else{
            throw new RuntimeException();
        }

    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupMapper.findAll();
    }

    //设置检查组合和检查项的关联关系
    public void setCheckGroupAndCheckItem(Integer checkGroupId,Integer[] checkitemIds){
        for (Integer checkitemId : checkitemIds) {
            Map<String,Integer> map = new HashMap<>();
            map.put("checkgroup_id",checkGroupId);
            map.put("checkitem_id",checkitemId);
            checkGroupMapper.setCheckGroupAndCheckItem(map);
        }

    }
}
