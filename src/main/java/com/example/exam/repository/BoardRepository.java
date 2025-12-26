package com.example.exam.repository;

import com.example.exam.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitleContainingOrContentContaining(String keyword1, String keyword2);

    // 최신 글 5개만 가져오기 (ID 역순으로 상위 5개)
    List<Board> findTop5ByOrderByIdDesc();

    List<Board> findByName(String name);
}