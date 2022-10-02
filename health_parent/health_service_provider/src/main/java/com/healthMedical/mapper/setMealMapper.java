package com.healthMedical.mapper;

import com.github.pagehelper.Page;
import com.healthMedical.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface setMealMapper {
    void add(Setmeal setmeal);
    void addCheckGroups(Map<String, Integer> map);

    Page<Setmeal> findSetMealByCondition(String value);

    String getFileName(Integer id);

    void deleteSetMealById(Integer id);

    void deleteCheckGroupAndSetMealById(Integer id);

    Setmeal findSetMealById(Integer id);

    List<Integer> findCheckGroupsById(Integer id);

    void update(Setmeal setmeal);

    void delete(Integer id);

    void updateSetMealAndCheckGroup(Map<String, Integer> map);
}
