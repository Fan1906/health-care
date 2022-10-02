package com.healthMedical.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.healthMedical.entity.PageResult;
import com.healthMedical.entity.QueryPageBean;
import com.healthMedical.mapper.checkitemMapper;
import com.healthMedical.pojo.CheckItem;
import com.healthMedical.service.checkitemService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

// 如果使用事务枚举需要明确确定实现的是哪个端口
@Service(interfaceClass = checkitemService.class)
@Transactional
public class checkitemServiceImpl implements checkitemService {
    @Resource
    private checkitemMapper checkitemMapper;


    @Override
    public void add(CheckItem checkItem) {
        checkitemMapper.add(checkItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage=queryPageBean.getCurrentPage();
        Integer pageSize=queryPageBean.getPageSize();
        String queryString=queryPageBean.getQueryString();
        //查询
        PageHelper.startPage(currentPage,pageSize);

        Page<CheckItem> checkItems = checkitemMapper.selectByCondition(queryString);
        long total = checkItems.getTotal();
        List<CheckItem> list = checkItems.getResult();
        return new PageResult(total,list);

    }

    //根据ID删除检查项
    @Override
    public void deleteById(Integer id) {
        //判断当前检查项是否关联到检查组
        long countByCheckitemId = checkitemMapper.findCountByCheckitemId(id);
        if(countByCheckitemId>0){
            //检查项已经关联到检查组，无法删除
            throw  new RuntimeException();
        }else{
            checkitemMapper.deleteById(id);
        }
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkitemMapper.edit(checkItem);
    }

    @Override
    public CheckItem findCheckitemById(Integer id) {
        CheckItem checkitem = checkitemMapper.findCheckitemById(id);
        return checkitem;
    }

    @Override
    public List<CheckItem> findAll() {
        return checkitemMapper.findAll();
    }
}