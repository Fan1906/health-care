package com.healthMedical.mapper;
import com.github.pagehelper.Page;
import com.healthMedical.entity.PageResult;
import com.healthMedical.entity.QueryPageBean;
import com.healthMedical.pojo.CheckItem;

import java.util.List;


public interface checkitemMapper {
    public void add(CheckItem checkItem);
    //使用分页插件 返回类型为page
    public Page<CheckItem> selectByCondition(String value);
    void deleteById(Integer id);
    public long findCountByCheckitemId(Integer id);
    public void edit(CheckItem checkItem);

    CheckItem findCheckitemById(Integer id);

    List<CheckItem> findAll();
}
