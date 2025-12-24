package com.example.exam.service;

import com.example.exam.DTO.BoardForm;
import com.example.exam.ExamApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ExamApplication.class)

class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Test
    void getBoard() {

        //1. 예상
        BoardForm expected = new BoardForm(4L, "제목3", "내용3");

        //2. 실제
        BoardForm dto = boardService.getBoard(4L);

        //3. 비교
        assertEquals(expected.toString(), dto.toString());
        assertNotNull(dto);
    }

    @Test
    void updateBoard() {

        //1. 예상 객체
        BoardForm expected = new BoardForm(1L, "junit 수정 테스트", "junit 내용테스트");

        //2. 실제 객체
        BoardForm dto = boardService.updateBoard(expected, 4L);

        //3. 비교
        assertEquals(expected.toString(), dto.toString());
        //assertNotNull(dto);
    }
}