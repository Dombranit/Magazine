package kz.bitlab.Magazine.service.impl;


import kz.bitlab.Magazine.Entity.OrderStatus;
import kz.bitlab.Magazine.Entity.Orders;
import kz.bitlab.Magazine.Entity.Users;
import kz.bitlab.Magazine.repository.OrderRepository;
import kz.bitlab.Magazine.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public List<Orders> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Orders getOrder(Long id) {
        return orderRepository.getReferenceById(id);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void saveOrder(Orders orders) {
        orderRepository.save(orders);
    }

    @Override
    public List<Orders> sortedOrders(Users users) {
        return orderRepository.findAllByUser(users);
    }
}
