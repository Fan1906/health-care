package com.healthMedical.mapper;

import com.github.pagehelper.Page;
import com.healthMedical.pojo.CheckGroup;

import java.util.List;
import java.util.Map;

public interface checkGroupMapper {

    void add(CheckGroup checkGroup);

    void setCheckGroupAndCheckItem(Map<String, Integer> map);

    Page<CheckGroup> findByCondition(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemByCheckGroupId(Integer id);

    void deleteGroupAndCheckItemById(Integer id);


    void editCheckGroup(CheckGroup checkGroup);

    void deleteCheckGroupById(Integer id);

    List<CheckGroup> findAll();

    Integer findSetMealByCheckGroupId(Integer id);
}
