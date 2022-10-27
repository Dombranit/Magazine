package kz.bitlab.Magazine.service;

import kz.bitlab.Magazine.Entity.Orders;
import kz.bitlab.Magazine.Entity.Users;

import java.util.List;

public interface OrderService {
    List<Orders> getOrders();
    Orders getOrder(Long id);
    void deleteOrder(Long id);
    void saveOrder(Orders orders);

    List<Orders> sortedOrders(Users users);

}
