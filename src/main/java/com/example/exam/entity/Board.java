package com.example.exam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter

// 자바로 만든 테이블 개념
public class Board {

    @Id // 기본키를 의미
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 자동 증가 설정방법
    private Long id;

    @Column // 테이블의 컬럼을 의미
    private String name;

    @Column
    private String title;

    @Column
    private String content;
}