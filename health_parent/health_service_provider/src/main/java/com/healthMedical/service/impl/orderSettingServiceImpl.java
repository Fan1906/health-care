package com.healthMedical.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.healthMedical.mapper.orderSettingMapper;
import com.healthMedical.pojo.OrderSetting;
import com.healthMedical.service.orderSettingService;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = orderSettingService.class)
@Transactional
public class orderSettingServiceImpl implements orderSettingService {
    @Resource
    private orderSettingMapper orderSettingMapper;
    //批量添加
    @Override
    public void add(List<OrderSetting> orderSettingList) {
        for (OrderSetting orderSetting : orderSettingList) {
            //检查此数据（日期）是否存在
            long count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
            if (count > 0) {
                //已经存在，执行更新操作
                orderSettingMapper.editNumberByOrderDate(orderSetting);
            } else {
                //不存在，执行添加操作
                orderSettingMapper.add(orderSetting);
            }
        }
    }


    //根据月份查询预约数据
    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        //格式 YYYY-MM
        String begin=date+"-1";
        String end=date+"-31";
        Map<String,String> map=new HashMap<>();
        map.put("begin",begin);
        map.put("end",end);
        //调用DAO，根据日期范围查询预约设置信息
        List<OrderSetting>list= orderSettingMapper.getOrderSettingByMonth(map);

        List<Map> data = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map orderSettingMap = new HashMap();
            orderSettingMap.put("date",orderSetting.getOrderDate().getDate());//获得日期（几号）
            orderSettingMap.put("number",orderSetting.getNumber());//可预约人数
            orderSettingMap.put("reservations",orderSetting.getReservations());//已预约人数
            data.add(orderSettingMap);
        }
        return data;
    }

    //根据日期修改可预约人数
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = orderSettingMapper.findCountByOrderDate(orderSetting.getOrderDate());
        if(count > 0){
            //当前日期已经进行了预约设置，需要进行修改操作
            orderSettingMapper.editNumberByOrderDate(orderSetting);
        }else{
            //当前日期没有进行预约设置，进行添加操作
            orderSettingMapper.add(orderSetting);
        }
    }
}
