package com.example.exam.controller;

import com.example.exam.DTO.BoardForm;
import com.example.exam.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class BoardApiController {

    @Autowired
    BoardService boardService;

    @GetMapping("/api/boards")
    public ResponseEntity<?> listBoard(@RequestParam String search) {

        // 서비스 메소드 호출
        List<BoardForm> boardList = boardService.getBoardList(search);
        return ResponseEntity.ok(boardList);
    }

    @PostMapping("/api/boards")
    public ResponseEntity<?> createBoard(@RequestBody BoardForm form) {
        BoardForm saved = boardService.createBoard(form);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/api/boards/{id}")
    public ResponseEntity<?> updateBoard(@PathVariable Long id,
                                         @RequestBody BoardForm form) {
        log.info("id : {} ",id);
        BoardForm updated = boardService.updateBoard(form, id);
        return ResponseEntity.ok(updated);
    }
}
