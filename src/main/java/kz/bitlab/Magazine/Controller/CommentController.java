package kz.bitlab.Magazine.Controller;

import kz.bitlab.Magazine.Entity.Comments;
import kz.bitlab.Magazine.Entity.Product;
import kz.bitlab.Magazine.Entity.Users;
import kz.bitlab.Magazine.service.CommentService;
import kz.bitlab.Magazine.service.ProductService;
import kz.bitlab.Magazine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/comment")
public class CommentController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public String addComment (@RequestParam(name = "product_id")Long productId,
                              @RequestParam(name = "comment_title")String comment){
        Comments comments = new Comments();
        comments.setComment(comment);
        commentService.addComment(comments,productId);
        return "redirect:/product/"+productId;
    }
    @PostMapping(value = "/delete")
    public String deleteComment(@RequestParam(name = "comment_id") Long id){
        commentService.deleteComment(id);
        return "redirect:/product";
    }
}
