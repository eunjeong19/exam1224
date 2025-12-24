package com.example.exam.DTO;

import com.example.exam.entity.Board;
import com.example.exam.entity.Comment;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class CommentForm {

    private Long id;
    private String content;
    private String name;
    private Long board_id;

    // DTO를 엔티티로 변환
    public Comment toEntity(){
        Board board = new Board(); // 엔티티 객체 생성
        board.setId(board_id);   // id 만 설정
        return new Comment(id, content, name, board);
    }

    // 엔티티를 DTO로 변환
    public static CommentForm toDto(Comment comment){
        return new CommentForm(comment.getId(), comment.getContent(),
                comment.getName(), comment.getBoard().getId());
    }
}
