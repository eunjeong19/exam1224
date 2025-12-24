package com.example.exam.service;

import com.example.exam.DTO.CommentForm;
import com.example.exam.entity.Comment;
import com.example.exam.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public List<CommentForm> getCommentList(Long board_id){

        return commentRepository.findByBoard_id(board_id)
                .stream()
                .map(CommentForm::toDto)
                .toList();
    }

    public CommentForm insertComment(CommentForm commentForm){
        try{
            log.info("DTO 객체 {}", commentForm.toString());
            Comment comment = commentRepository.save(commentForm.toEntity());
            log.info("엔티티 객체 {}", comment.toString());
            return CommentForm.toDto(comment);
        } catch(Exception e){
            return null;
        }
    }

    public CommentForm getComment(Long id){
        Optional<Comment> optionalComment = commentRepository.findById(id);

        if(optionalComment.isPresent()){
            return CommentForm.toDto(optionalComment.get());
        } else {
            return null;
        }
    }

    @Transactional
    public CommentForm updateComment(CommentForm commentForm){
        log.info("DTo 객체 {}", commentForm.toString());
        Comment comment = commentRepository.findById(commentForm.getId())
                .orElseThrow(()->new IllegalArgumentException("댓글 없음"));

        Comment updated = commentRepository.save(commentForm.toEntity());
        log.info("엔티티 객체 {}",updated.toString());

        return CommentForm.toDto(updated);
    }

    public boolean deleteComment(Long id){

        Optional<Comment> optionalComment = commentRepository.findById(id);
        if(optionalComment.isPresent()){
            commentRepository.delete(optionalComment.get());
            return true;
        } else {
            return false;
        }
    }

    public Long getCountComment(Long board_id){
        return commentRepository.countByBoard_id(board_id);
    }
}
