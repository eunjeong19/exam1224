package com.example.exam.DTO;

import com.example.exam.entity.Member;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class MemberForm {


    private String id;
    private String password;
    private String name;
    private int age;
    private String addr;
    private String mobile;

    //DTO를 Entity로 변환
    // DTO에 추가하기
    public Member toEntity() {
        return new Member(id, password, name, age, addr, mobile);
    }

    // Entity를 DTO 로 변환
    public static MemberForm toDto(Member mem){
        return new MemberForm(mem.getId(),mem.getPassword(), mem.getName(),
                mem.getAge(), mem.getAddr(), mem.getMobile());
    }
}
