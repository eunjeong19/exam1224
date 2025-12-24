package com.example.exam.repository;

import com.example.exam.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository <Comment, Long> {

    List<Comment> findByBoard_id(Long board_id);

    // 게시글 ID에 해당하는 댓글의 총 개수를 구하는 함수
    long countByBoard_id(Long board_id);
}
