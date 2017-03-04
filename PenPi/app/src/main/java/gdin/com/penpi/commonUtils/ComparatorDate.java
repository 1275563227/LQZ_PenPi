package gdin.com.penpi.commonUtils;

import java.util.Comparator;
import java.util.Date;

import gdin.com.penpi.domain.Order;

/**
 * 项目名称：PenPi
 * 作者：Administrator
 * 时间：2016.12.05
 */
public class ComparatorDate implements Comparator {

    public int compare(Object obj1, Object obj2) {
        Order order1 = (Order) obj1;
        Order order2 = (Order) obj2;

        Date d1, d2;
        try {
            d1 = order1.getSendOrderDate();
            d2 = order2.getSendOrderDate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

        if (d1.before(d2)) {
            return 1;
        } else {
            return -1;
        }
    }
}
