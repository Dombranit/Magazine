package kz.bitlab.Magazine.repository;

import kz.bitlab.Magazine.Entity.Korzina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface KorzinaRepository extends JpaRepository<Korzina,Long> {
}
