package gdin.com.penpi.util;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import gdin.com.penpi.bean.Order;

/**
 * 项目名称：PenPi
 * 作者：Administrator
 * 时间：2016.12.05
 */
public class ComparatorDate implements Comparator {


    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public int compare(Object obj1, Object obj2) {
        Order order1 = (Order) obj1;
        Order order2 = (Order) obj2;

        Date d1,d2;
        try {
            d1 = format.parse(order1.getDate());
            d2 = format.parse(order2.getDate());
        } catch (Exception e) {
            // TODO: handle exception
            return 0;
        }

        if (d1.before(d2)) {
            return 1;
        } else {
            return -1;
        }
    }
}
