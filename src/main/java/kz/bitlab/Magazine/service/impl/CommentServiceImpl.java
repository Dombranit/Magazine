package kz.bitlab.Magazine.service.impl;

import kz.bitlab.Magazine.Entity.Comments;
import kz.bitlab.Magazine.Entity.Product;
import kz.bitlab.Magazine.Entity.Users;
import kz.bitlab.Magazine.repository.CommentRepository;
import kz.bitlab.Magazine.service.CommentService;
import kz.bitlab.Magazine.service.ProductService;
import kz.bitlab.Magazine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Override
    public List<Comments> getComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comments addComment(Comments comments,Long productId) {
        Users users = userService.getUserData();
        Product product = productService.getProductById(productId);
        comments.setProduct(product);
        comments.setUsers(users);
        return commentRepository.save(comments);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
