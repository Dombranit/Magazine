package kz.bitlab.Magazine.service;

import kz.bitlab.Magazine.Entity.Comments;

import java.util.List;

public interface CommentService {
    List<Comments> getComments();
    Comments addComment(Comments comments,Long productId);
    void deleteComment(Long id);
}
