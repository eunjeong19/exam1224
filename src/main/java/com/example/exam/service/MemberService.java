package com.example.exam.service;

import com.example.exam.DTO.MemberForm;
import com.example.exam.entity.Member;
import com.example.exam.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MemberService {
    @Autowired
    MemberRepository memberRepository;

    // Member 전체 리스트 호출
    public List<MemberForm> getMemberList(){
        // 엔티티를 DTO로 변환
        return memberRepository.findAll()
                .stream()
                .map(MemberForm::toDto)
                .toList();
    }

    // Member insert 하기
    public MemberForm creatMember(MemberForm mform){
        try{
            log.info("DTO 객체 {}", mform.toString());
            Member member = memberRepository.save(mform.toEntity());
            log.info("엔티티 객체 {}", member.toString());
            // 엔티티 객체를 DTO로
            return MemberForm.toDto(member);
        } catch(Exception e){
            throw new RuntimeException("DB 저장 중 오류가 발생했습니다.",e);
        }
    }

    // id 이용해서 Member 1명 정보 select
    public MemberForm getMember(String id){
        Optional<Member> optionalMember = memberRepository.findById(id);

        if(optionalMember.isPresent()){
            return MemberForm.toDto(optionalMember.get());
        } else{
            return null;
        }
    }

    // Member 정보 수정하기
    @Transactional
    public MemberForm updateMember(MemberForm mform){
        log.info("DTO 객체 {}", mform.toString());

        // 1. 기존 내용이 있는지 확인(아이디로)
        Member member = memberRepository.findById(mform.getId())
            .orElseThrow(() -> new IllegalArgumentException("수정 실패, 해당 멤버가 존재하지 않습니다."));

        Member updated = memberRepository.save(mform.toEntity());
        log.info("엔티티 객체 {}", updated.toString());

        // 2. 엔티티를 DTO로 변환
        return MemberForm.toDto(updated);
    }

    // 회원 삭제
    public boolean deleteMember(String id){

        // 1. 기존 회원이 있는지 확인(id로)
        Optional<Member> optionalMember = memberRepository.findById(id);

        if(optionalMember.isPresent()){
            memberRepository.delete(optionalMember.get());
            return true;
        } else {
            return false;
        }
    }

    // 회원 로그인
    public MemberForm login(MemberForm form){
        Optional<Member> optionalMember = memberRepository.findById(form.getId());

        if(optionalMember.isPresent()){
            if(!optionalMember.get().getPassword().equals(form.getPassword())){
                return null;
            }
            return MemberForm.toDto(optionalMember.get());
        } else {
            return null;
        }
    }

    // 전체 멤버 카운트
    public long countTotal(){
        return memberRepository.count();
        // JPA가 자동으로 "select count(*);
    }
}


























