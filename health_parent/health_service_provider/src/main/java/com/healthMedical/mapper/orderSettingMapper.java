package com.healthMedical.mapper;

import com.healthMedical.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface orderSettingMapper {
    public long findCountByOrderDate(Date orderDate);

    public void editNumberByOrderDate(OrderSetting orderSetting) ;
    public void add(OrderSetting orderSetting) ;

    List<OrderSetting> getOrderSettingByMonth(Map<String, String> map);
}
