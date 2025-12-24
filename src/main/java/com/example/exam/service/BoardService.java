package com.example.exam.service;

import com.example.exam.DTO.BoardForm;
import com.example.exam.entity.Board;
import com.example.exam.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;

    // Service : CRUD 호출, 비지니스 로직, 예외처리
    // 전체 리스트 호출
    public List<BoardForm> getBoardList(String search) {
        // 엔티티를 DTO로 변환

        /*List<Board> board = boardRepository.findAll();
        List<BoardForm> bform = new ArrayList<>();
        for(Board obj : board){
            bform.add(BoardForm.toDto(obj));
        }*/
        try{if(search == null || search.isBlank()) {
            return boardRepository.findAll()
                    .stream()
                    .map(BoardForm::toDto)
                    .toList();
        }else{
            return boardRepository.findByTitleContainingOrContentContaining(search,search)
                    .stream()
                    .map(BoardForm::toDto)
                    .toList();
        }
        } catch (Exception e) {
            throw new RuntimeException("DB 조회 중 오류가 발생했습니다.",e);
        }
    }

    // insert
    public BoardForm createBoard(BoardForm bform) {

        // 1. 유효성 검증
        if(bform.getTitle()==null || bform.getTitle().isBlank()){
            throw new IllegalArgumentException("제목은 필수 입력값입니다.");
        }
        if(bform.getContent()==null || bform.getContent().isBlank()){
            throw new IllegalArgumentException("내용은 필수 입력값입니다.");
        }
        try {
            log.info("DTO 객체 {}", bform.toString());
            Board board = boardRepository.save(bform.toEntity());
            log.info("엔티티 객체 {}", board.toString());
            return BoardForm.toDto(board);
        } catch (Exception e) {
            throw new RuntimeException("DB 저장 중 오류가 발생했습니다.",e);
        }
    }

    // id 이용해서 게시글 1개 select
    public BoardForm getBoard(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);

        if (optionalBoard.isPresent()) {
            return BoardForm.toDto(optionalBoard.get());
        } else {
            return null;
        }
    }

    @Transactional
    public BoardForm updateBoard(BoardForm form, Long id) {
        log.info("DTO 객체 {}",form.toString());

        // 1. 기존 내용이 있는지 확인
        Board board = boardRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("수정 실패! 대상 글이 없습니다."));
        try {
            Board updated = boardRepository.save(form.toEntity());
            log.info("엔티티 객체 {}", updated.toString());

            // 2. 엔티티를 DTO 로 변환 해서 리턴
            return BoardForm.toDto(updated);
        } catch (Exception e) {
            throw new RuntimeException("DB 수정 중 오류가 발생했습니다.", e);
        }
    }

    // 내용 수정하기
    @Transactional
    public BoardForm updateBoard(BoardForm bform) {
        log.info("DTO 객체 {}", bform.toString());
        /*트랜잭션 테스트
        Board aaa = bform.toEntity();
        aaa.setId(null);
        boardRepository.save(aaa);*/

        // 1. 기존 내용이 있는지 확인
        Board board = boardRepository.findById(bform.getId())
                .orElseThrow(() -> new IllegalArgumentException("수정 실패, 해당 게시글이 없습니다."));

        Board updated = boardRepository.save(bform.toEntity());
        log.info("엔티티 객체 {}", updated.toString());

        // 2. 엔티티를 DTO로 변환해서 리턴
        return BoardForm.toDto(updated);
    }

    public boolean deleteBoard(Long id) {

        // 1. 기존 내용이 있는지 확인
        Optional<Board> optionalBoard = boardRepository.findById(id);

        if (optionalBoard.isPresent()) {
            boardRepository.delete(optionalBoard.get());
            return true;
        } else{
            return false;
        }
    }

    // 전체 게시글 개수 카운트
    public long countTotal(){
        return boardRepository.count();
        // JPA가 자동으로 "select count(*);
    }

    // 최신 게시글 5개만 가져오는 기능
    public List<Board> findTop5(){
        return boardRepository.findTop5ByOrderByIdDesc();
    }
}
























