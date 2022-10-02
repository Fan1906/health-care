package com.healthMedical.service;


import com.healthMedical.entity.PageResult;
import com.healthMedical.entity.QueryPageBean;
import com.healthMedical.pojo.Setmeal;

import java.util.List;

public interface setMealService {
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult findPage(QueryPageBean queryPageBean);

    void deleteById(Integer id);

    Setmeal findSetMealById(Integer id);

    List<Integer> selectCheckGroupBySetMealId(Integer id);

    void update(Setmeal setmeal,Integer [] ids);
}
