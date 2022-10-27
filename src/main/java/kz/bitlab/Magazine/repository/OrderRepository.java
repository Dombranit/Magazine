package kz.bitlab.Magazine.repository;

import kz.bitlab.Magazine.Entity.OrderStatus;
import kz.bitlab.Magazine.Entity.Orders;
import kz.bitlab.Magazine.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {


    List<Orders> findAllByUser (Users users);
}
