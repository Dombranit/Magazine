package kz.bitlab.Magazine.Controller;

import kz.bitlab.Magazine.Entity.OrderStatus;
import kz.bitlab.Magazine.Entity.Orders;
import kz.bitlab.Magazine.service.OrderService;
import kz.bitlab.Magazine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping(value = "/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public String openOrderChange(Model model) {
        List<Orders> ordersList = orderService.getOrders();
        model.addAttribute("orders", ordersList);
        model.addAttribute("currentUser", userService.getUserData());
        return "order_change";
    }

    @GetMapping(value = "/{id}")
    public String getOrder(Model model, @PathVariable(name = "id") Long id) {
        Orders orders = orderService.getOrder(id);
        model.addAttribute("order", orders);
        model.addAttribute("currentUser", userService.getUserData());
        model.addAttribute("orderStatusN", OrderStatus.NEW);
        model.addAttribute("orderStatusA", OrderStatus.APPROVED);
        model.addAttribute("orderStatusC", OrderStatus.CANCELED);
        model.addAttribute("orderStatusClose", OrderStatus.CLOSED);
        model.addAttribute("orderStatusPaid", OrderStatus.PAID);
        return "order";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_MODERATOR')")
    public String updateOrder(@RequestParam(name = "order_id") Long id,
                              @RequestParam(name = "order_status") OrderStatus orderStatus) {
        Orders orders = orderService.getOrder(id);
        orders.setOrderStatus(orderStatus);
        orderService.saveOrder(orders);
        return "redirect:/order";
    }

    @GetMapping(value = "/myOrder")
    @PreAuthorize("isAuthenticated()")
    public String myOrder(Model model) {
        List<Orders> ordersList = orderService.sortedOrders(userService.getUserData());
        model.addAttribute("orders", ordersList);
        model.addAttribute("currentUser", userService.getUserData());
        return "my_orders";
    }

    @GetMapping(value = "/closed/{id}")
    public String openClosedOrder(Model model, @PathVariable(name = "id") Long id) {
        Orders orders = orderService.getOrder(id);
        model.addAttribute("UserOrder", orders);
        model.addAttribute("currentUser", userService.getUserData());
        return "closed_order";
    }
    @GetMapping(value = "/pay/{id}")
    public String getPayOrder(Model model, @PathVariable(name = "id") Long id) {
        Orders orders = orderService.getOrder(id);
        model.addAttribute("UserOrder", orders);
        model.addAttribute("currentUser", userService.getUserData());
        return "pay_order";
    }

    @PostMapping(value = "/pay")
    public String payOrder(@RequestParam(name = "user_address") String address,
                           @RequestParam(name = "user_price") BigDecimal price,
                           @RequestParam(name = "order_id") Long orderId) {
        Orders orders = orderService.getOrder(orderId);
        if (orders.getTotalPrice().equals(price)) {
            orders.setAddress(address);
            orders.setOrderStatus(OrderStatus.PAID);
            orderService.saveOrder(orders);
        }
        return "redirect:/order/myOrder";
    }
}
