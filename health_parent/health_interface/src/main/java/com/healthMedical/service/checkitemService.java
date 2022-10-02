package com.healthMedical.service;

import com.healthMedical.entity.PageResult;
import com.healthMedical.entity.QueryPageBean;
import com.healthMedical.entity.Result;
import com.healthMedical.pojo.CheckItem;

import java.util.List;

//服务接口
public interface checkitemService {
    public void add(CheckItem checkItem);

    public PageResult findPage(QueryPageBean queryPageBean);

    void deleteById(Integer id);

    void edit(CheckItem checkItem);

    CheckItem findCheckitemById(Integer id);

    List<CheckItem> findAll();
}
