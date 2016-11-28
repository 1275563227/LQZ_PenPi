package gdin.com.penpi.util;

import gdin.com.penpi.bean.Order;

/**
 * 项目名称：PenPi
 * 作者：Administrator
 * 时间：2016.11.27
 */
public class StringSpilt {

    public static Order messageToOrder(String message){

        Order order = new Order();
        String[] spilts = message.split(",");
        order.setStart_place(spilts[0]);
        order.setEnd_place(spilts[1]);
        order.setName(spilts[2]);
        order.setPhone_number(spilts[3]);
        order.setCharges(spilts[4]);
        order.setRemark(spilts[5]);
        order.setState(spilts[6]);
        return order;
    }
}
