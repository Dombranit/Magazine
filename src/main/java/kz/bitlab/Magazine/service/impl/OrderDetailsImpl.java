package kz.bitlab.Magazine.service.impl;

import kz.bitlab.Magazine.Entity.*;
import kz.bitlab.Magazine.repository.OrderDetailsRepository;
import kz.bitlab.Magazine.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsImpl implements OrderDetailsService {
    @Autowired
    public OrderDetailsRepository orderDetailsRepository;

}
