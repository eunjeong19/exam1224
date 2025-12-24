package com.example.exam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Member {

    @Id
    private String id;

    @Column
    private String password;
    @Column
    private String name;
    @Column
    private int age;
    @Column
    private String addr;
    @Column
    private String mobile;
}
