package kz.bitlab.Magazine.repository;

import kz.bitlab.Magazine.Entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface CommentRepository extends JpaRepository<Comments,Long> {
}
