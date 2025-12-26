package com.example.exam.controller;

import com.example.exam.DTO.BoardForm;
import com.example.exam.DTO.CommentForm;
import com.example.exam.DTO.MemberForm;
import com.example.exam.entity.Board;
import com.example.exam.repository.BoardRepository;
import com.example.exam.service.BoardService;
import com.example.exam.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class BoardController {

    @Autowired
    BoardService boardService;

    @Autowired
    CommentService commentService;

    @GetMapping("/board")
    public String listBoard(Model model) {
        return "board/list";
    }

    // 입력 양식 페이지
    @GetMapping("/board/input")
    public String inputBoard(){
        return "board/input";
    }

    // 새 글 등록
    @PostMapping("/board/create")
    public String createBoard(BoardForm bform, RedirectAttributes rttr) {

        BoardForm saved = boardService.createBoard(bform);
        if(saved != null) {
            rttr.addFlashAttribute("msg", "title이 "+ bform.getTitle()+"인 새 글이 등록되었습니다.");
            return "redirect:/board";
        } else {
            return "error/errorAll";
        }
    }

    @GetMapping("/board/info/{id}")
    public String infoBoard(@PathVariable Long id, Model model) {

        BoardForm saved = boardService.getBoard(id);
        if (saved != null) {
            List<CommentForm> list = commentService.getCommentList(id);
            model.addAttribute("board", saved);
            model.addAttribute("comment", list);

            // 게시물의 댓글 개수 가지고 오기
            Long counted = commentService.getCountComment(id);
            model.addAttribute("count", counted);
            return "board/info";
        } else {
            return "error/errorAll";
        }
    }

    @GetMapping("/board/edit")
    public String editBoard(@RequestParam Long id, Model model) {

        BoardForm saved = boardService.getBoard(id);
        if (saved != null) {
            model.addAttribute("board", saved);
            return "board/edit";
        } else {
            return "error/errorAll";
        }
    }

    @PostMapping("/board/modify")
    public String modifyBoard(BoardForm bform, RedirectAttributes rttr) {

        BoardForm updated = boardService.updateBoard(bform);

        if (updated != null) {
            rttr.addFlashAttribute("msg","title : "+bform.getTitle()+"이 수정되었습니다.");
            return "redirect:/board";
        } else {
            return "error/errorAll";
        }
    }

    @GetMapping("board/delete")
    public String deleteBoard(@RequestParam Long id, RedirectAttributes rttr) {

        boolean del = boardService.deleteBoard(id);
        if(del == true){
            rttr.addFlashAttribute("msg","No."+id+"의 게시물이 삭제되었습니다.");
            return "redirect:/board";
        } else{
            return "error/errorAll";
        }
    }

    @GetMapping("/board/mylist")
    public String listMyBoard(HttpSession session, Model model){
        // 1. 세션에서 로그인 유저 정보 가져오기
        MemberForm user = (MemberForm)session.getAttribute("User");
        // System.out.println(user.getId());

        // 2. 로그인 여부 확인
        if(user == null){
            return "redirect:/member/memberLogin";
            // 로그인 안 되었으면 로그인 페이지로
        }

        // 3. 본인 이름으로 작성된 글만 가져오기
        List<Board> myBoard = boardService.getMyList(user.getName());

        // 4. model에 담아서 뷰로 전달
        model.addAttribute("mylist", myBoard);
        model.addAttribute("count", myBoard.size());

        return "board/mylist";
    }
}
