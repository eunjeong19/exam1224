package com.example.exam.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Comment {

    @Id // 기본키를 의미
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 자동 증가 설정방법
    private Long id;

    @Column // 테이블의 컬럼을 의미
    private String content;

    @Column
    private String name;

    // 외래키 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id") // 대신, 외래키의 컬럼 이름을 지정
    private Board board; // 어떤 엔티티를 참조할 것인지 엔티티 객체를 설정한다.
}
