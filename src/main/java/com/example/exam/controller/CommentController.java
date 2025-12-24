package com.example.exam.controller;

import com.example.exam.DTO.CommentForm;
import com.example.exam.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/comment/create")
    public String createComment(CommentForm commentForm,
                                RedirectAttributes rttr){

        CommentForm saved = commentService.insertComment(commentForm);
        if(saved != null) {
            rttr.addFlashAttribute("msg","댓글이 등록되었습니다.");
            //board/info/id
            return "redirect:/board/info/" + saved.getBoard_id();
        } else {
            return "error/errorAll";
        }
    }

    @GetMapping("/comment/commentEdit/{id}")
    public String editComment(@PathVariable Long id, Model model){
        CommentForm saved = commentService.getComment(id);
        System.out.println(id);
        if(saved != null){
            model.addAttribute("comment",saved);
            return "comment/commentEdit";
        } else {
            return "error/errorAll";
        }
    }

    @PostMapping("/comment/modify")
    public String modifyComment(CommentForm commentform,
                                RedirectAttributes rttr){
        CommentForm updated = commentService.updateComment(commentform);
        if(updated != null){
            rttr.addFlashAttribute("msg","댓글이 수정되었습니다.");
            return "redirect:/board/info/"+ updated.getBoard_id();
        } else {
            return "error/errorAll";
        }
    }

    @GetMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable Long id,
                                @RequestParam Long board_id,
                                RedirectAttributes rttr){
        boolean del = commentService.deleteComment(id);
        if(del == true) {
            rttr.addFlashAttribute("msg","해당 댓글이 삭제되었습니다.");
            return "redirect:/board/info/" + board_id;
        } else {
            return "error/errorAll";
        }
    }
}
