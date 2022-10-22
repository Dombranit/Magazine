package kz.bitlab.Magazine.repository;

import kz.bitlab.Magazine.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Users,Long> {

    Users findByEmail(String email);
}
