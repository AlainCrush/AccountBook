package com.example.accountbook;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GetWeeklyInterval {

    public List<Date> getWeekList() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会计算到下一周去
        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天是星期一
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - day);
        Date weekBegin = calendar.getTime();
        calendar.add(Calendar.DATE, 6);
        Date weekEnd = calendar.getTime();

        List<Date> weeklyInterval = new ArrayList();
        weeklyInterval.add(weekBegin);
        Calendar calendarBegin = Calendar.getInstance();
        // 使用weekDate设置Calendar的时间
        calendarBegin.setTime(weekBegin);
        Calendar calendarEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calendarEnd.setTime(weekEnd);
        // 测试此日期是否在指定日期之后
        while (weekEnd.after(calendarBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calendarBegin.add(Calendar.DAY_OF_MONTH, 1);
            weeklyInterval.add(calendarBegin.getTime());
        }
        return weeklyInterval;
    }

}
