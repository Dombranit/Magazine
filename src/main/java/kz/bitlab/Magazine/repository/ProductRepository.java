package kz.bitlab.Magazine.repository;

import kz.bitlab.Magazine.Entity.Category;
import kz.bitlab.Magazine.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByCategories(Category category);
}
