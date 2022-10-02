package com.healthMedical.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.healthMedical.constant.RedisConstant;
import com.healthMedical.entity.PageResult;
import com.healthMedical.entity.QueryPageBean;
import com.healthMedical.mapper.setMealMapper;
import com.healthMedical.pojo.Setmeal;
import com.healthMedical.service.setMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.healthMedical.utils.QiniuUtils.deleteFileFromQiniu;

@Service(interfaceClass = setMealService.class)
@Transactional
public class setMealServiceImpl implements setMealService {
    @Resource
    private setMealMapper setMealMapper;
    @Autowired
    private JedisPool jedisPool;

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //添加套餐
        setMealMapper.add(setmeal);
        //添加相关联的检查组
        if(checkgroupIds!=null && checkgroupIds.length>0 ){
            addCheckGroups(setmeal.getId(),checkgroupIds);
        }
        //保存图片名称
        String fileName = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,fileName);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage=queryPageBean.getCurrentPage();
        Integer pageSize= queryPageBean.getPageSize();
        String queryString=queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);

        Page<Setmeal> setMeals=setMealMapper.findSetMealByCondition(queryString);
        Long total=setMeals.getTotal();
        return new PageResult(total,setMeals);
    }

    @Override
    public void deleteById(Integer id) {
        //删除与套餐相关联的检查组（）在套餐检查组关联表
        setMealMapper.deleteCheckGroupAndSetMealById(id);
        //删除套餐
        setMealMapper.deleteSetMealById(id);
        //删除图片
        String filName=setMealMapper.getFileName(id);
        System.out.println(filName);
        deleteFileFromQiniu(filName);
    }

    @Override
    public Setmeal findSetMealById(Integer id) {
        Setmeal setmeal= setMealMapper.findSetMealById(id);
        return setmeal;
    }

    @Override
    public List<Integer> selectCheckGroupBySetMealId(Integer id) {
        List<Integer> list =setMealMapper.findCheckGroupsById(id);
        return list;
    }

    @Override
    public void update(Setmeal setmeal,Integer [] ids) {
        //修改套餐内容
        setMealMapper.update(setmeal);
        System.out.println(setmeal.toString());
        //修改与检查组的关系
        //删除
        setMealMapper.delete(setmeal.getId());
        //添加
        Map<String,Integer> map=new HashMap<>();
        if(ids!=null && ids.length>0){
            for(Integer checkGroup:ids){
                map.put("checkgroup_id",checkGroup);
                map.put("setmeal_id",setmeal.getId());
                setMealMapper.updateSetMealAndCheckGroup(map);
            }
        }

    }

    private void addCheckGroups(Integer id, Integer[] checkgroupIds) {
        for(Integer checkGroupId:checkgroupIds){
            Map<String,Integer> map=new HashMap<>();
            map.put("setmeal_id",id);
            map.put("checkgroup_id",checkGroupId);
            setMealMapper.addCheckGroups(map);
        }
    }
}
