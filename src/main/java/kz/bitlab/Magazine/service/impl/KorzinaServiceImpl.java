package kz.bitlab.Magazine.service.impl;

import kz.bitlab.Magazine.Entity.*;
import kz.bitlab.Magazine.dto.KorzinaDetailsDto;
import kz.bitlab.Magazine.dto.KorzinaDto;
import kz.bitlab.Magazine.repository.KorzinaRepository;
import kz.bitlab.Magazine.repository.ProductRepository;
import kz.bitlab.Magazine.service.KorzinaService;
import kz.bitlab.Magazine.service.OrderService;
import kz.bitlab.Magazine.service.UserService;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class KorzinaServiceImpl implements KorzinaService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private KorzinaRepository korzinaRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Override
    public Korzina createKorzina(Users users, Long id) {
        Korzina korzina = new Korzina();
        korzina.setUser(users);
        List<Product> products = Collections.singletonList(productRepository.getReferenceById(id));
        korzina.setProducts(products);
        return korzinaRepository.save(korzina);
    }

    @Override
    public void addProducts(Korzina korzina, Long id) {
        List<Product> products = korzina.getProducts();
        List<Product> newProducts = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProducts.add(productRepository.getReferenceById(id));
        korzina.setProducts(newProducts);
        korzinaRepository.save(korzina);
    }

    @Override
    public void removeProduct(Korzina korzina, Long id) {
        List<Product> products = korzina.getProducts();
        List<Product> newProducts = products == null ? new ArrayList<>() : new ArrayList<>(products);
        newProducts.remove(productRepository.getReferenceById(id));
        korzina.setProducts(newProducts);
        korzinaRepository.save(korzina);
    }

    @Override
    public KorzinaDto getKorzinaByEmail(String name) {
        Users users = userService.getUserByEmail(name);
        if (users == null || users.getKorzina() == null) {
            return new KorzinaDto();
        }
        KorzinaDto korzinaDto = new KorzinaDto();
        Map<Long, KorzinaDetailsDto> mapByProductId = new HashMap<>();

        List<Product> products = users.getKorzina().getProducts();
        for (Product product : products) {
            KorzinaDetailsDto detail = mapByProductId.get(product.getId());
            if (detail == null) {
                mapByProductId.put(product.getId(), new KorzinaDetailsDto(product));
            } else {
                detail.setAmount(detail.getAmount().add(new BigDecimal("1.0")));
                detail.setSum(detail.getSum() + Double.parseDouble(product.getPrice().toString()));
            }
        }
        korzinaDto.setKorzinaDetails(new ArrayList<>(mapByProductId.values()));
        korzinaDto.aggregate();

        return korzinaDto;
    }

    @Override
    public void commitKorzinaToOrder(String email) {
        Users users = userService.getUserByEmail(email);
        if (users == null) {
            throw new RuntimeException("/login");
        }
        Korzina korzina = users.getKorzina();
        if (korzina == null || korzina.getProducts().isEmpty()) {
            return;
        }
        Orders orders = new Orders();
        orders.setOrderStatus(OrderStatus.NEW);
        orders.setUser(users);

        Map<Product, Long> productWithAmount = korzina.getProducts().stream()
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));

        List<OrderDetails> orderDetails = productWithAmount.entrySet().stream()
                .map(pair -> new OrderDetails(orders,pair.getKey(),pair.getValue()))
                .collect(Collectors.toList());

        BigDecimal total = new BigDecimal(orderDetails.stream()
                .map(detail -> detail.getPrice().multiply(detail.getAmount()))
                .mapToDouble(BigDecimal::doubleValue).sum());

        orders.setOrderDetails(orderDetails);
        orders.setTotalPrice(total);
        orders.setAddress(users.getAddress());

        orderService.saveOrder(orders);
        korzina.getProducts().clear();
        korzinaRepository.save(korzina);

    }
}
