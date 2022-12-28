package kz.bitlab.Magazine.api;

import kz.bitlab.Magazine.Entity.Comments;
import kz.bitlab.Magazine.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/comment")
public class CommentRestController {
    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    List<Comments> getComments() {
        return commentService.getComments();
    }

    @PostMapping(value = "/{id}")
    Comments addComment(@RequestBody Comments comments,
                        @PathVariable(name = "id")Long id){
        return commentService.addComment(comments,id);
    }

    @DeleteMapping(value = "/{id}")
    void deleteComment(@PathVariable(name = "id")Long id){
        commentService.deleteComment(id);
    }
}
