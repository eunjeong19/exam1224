package com.example.exam.DTO;

import com.example.exam.entity.Board;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter

public class BoardForm {

    private Long id;
    private String name;
    private String title;
    private String content;

    //DTO를 Entity로 변환
    // DTO에 추가하기
    public Board toEntity() {
        return new Board(id, name, title, content);
    }

    //Entity를 DTO 로 변환
    // DTO에 추가하기
    public static BoardForm toDto(Board board) {
        return new BoardForm(board.getId(), board.getName(), board.getTitle(), board.getContent());
    }
}
