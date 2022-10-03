package com.healthMedical.service;


import com.healthMedical.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface orderSettingService {
    public void add(List<OrderSetting> orderSettingList);

    List<Map> getOrderSettingByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);
}
