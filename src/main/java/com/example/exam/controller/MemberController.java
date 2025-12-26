package com.example.exam.controller;

import com.example.exam.DTO.MemberForm;
import com.example.exam.DTO.MemberForm2;
import com.example.exam.entity.Board;
import com.example.exam.entity.Member;
import com.example.exam.repository.MemberRepository;
import com.example.exam.service.BoardService;
import com.example.exam.service.MemberService;
import jakarta.servlet.http.HttpSession;
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
import java.util.Optional;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    MemberService memberService;
    @Autowired
    BoardService boardService;

    // 회원 리스트 찾는 창
    @GetMapping("/member")
    public String mem(){
        return "/member/member";
    }

    // 회원 리스트 서비스 메소드 호출
    @GetMapping("/member/memberlist")
    public String listMember(Model model){

        List<MemberForm> memlist = memberService.getMemberList();
        model.addAttribute("memlist",memlist);
        return "/member/memberlist";
    }

    // 회원 추가 양식 페이지
    @GetMapping("/member/memberInput")
    public String inputMember(){
        return "member/memberInput";
    }

    // 회원 추가하기
    @GetMapping("/member/memberInput_ok")
    public String inputMember_ok(MemberForm mem, RedirectAttributes rttr){

        MemberForm saved = memberService.creatMember(mem);
        if(saved != null){
            rttr.addFlashAttribute("msg",mem.getName()+"회원님이 추가되었습니다.");
            return "redirect:/member/memberlist";
        } else{
            return "/error/errorAll";
        }
    }

    // 회원 id를 이용해서 회원 정보 select 해오기
    @GetMapping("member/memberInfo/{id}")
    public String memInfo(@PathVariable String id, Model model){

        MemberForm saved = memberService.getMember(id);
        log.info(id);
        if(saved != null){
            model.addAttribute("mem", saved);
            return "/member/memberInfo";
        } else{
            return "/error/errorAll";
        }
    }

    // 회원 정보 수정 전 가져오기
    @GetMapping("member/memberEdit/{id}")
    public String memEdit(@PathVariable String id, Model model){

        MemberForm saved = memberService.getMember(id);

        if(saved != null){
            model.addAttribute("member",saved);
            return "member/memberEdit";
        } else{
            return "/error/errorAll";
        }
    }

    // 회원 정보 수정하기
    @PostMapping("member/memberModify")
    public String modifyMember(MemberForm mform, RedirectAttributes rttr) {
        MemberForm updated = memberService.updateMember(mform);

        if(updated != null){
            rttr.addFlashAttribute("msg",mform.getName()+"님의 정보가 수정되었습니다.");
            return "redirect:/member/memberlist";
        } else {
            return "/error/errorAll";
        }
    }

    // 회원 정보 삭제하기
    @GetMapping("member/memberDelete/{id}")
    public String memDelete(@PathVariable String id, RedirectAttributes rttr){

        boolean del = memberService.deleteMember(id);

        if(del == true){
            // 해당 id의 데이터가 있으면 Entity 받아서 삭제
            rttr.addFlashAttribute("msg",id+"가 삭제되었습니다.");
            return "redirect:/member/memberlist";
        } else{
            return "/error/errorAll";
        }
    }

    @GetMapping("/member/memberLogin")
    public String loginMember(@RequestParam(required = false) String msg,
                              @RequestParam(required = false) String link, Model model){
        if("required".equals(msg)){
            model.addAttribute("message","로그인이 필요한 페이지입니다.");
            // 이전페이지 정보 추가
            model.addAttribute("link", link);
            System.out.println("이전 페이지 정보" + link);
        }
        return "member/memberLogin";
    }

    // 아이디/비밀번호 검증 + 세션 생성
    @PostMapping("/member/auth")
    public String authMember(MemberForm form, HttpSession session,
                             @RequestParam(required = false) String link){
        log.info("dto {}", form.toString());
        MemberForm loginedMember = memberService.login(form); // 로그인처리
        session.setAttribute("User", loginedMember); // 세션에 로그인 정보 저장

        MemberForm user = (MemberForm)session.getAttribute("User"); // 세션 확인
        log.info("세션 정보 : {}", user.getId());

        // 이전 페이지 리다이렉트 수정
        log.info("이전 페이지 : {}", link);
        if(link == null || link.isBlank()){
            return "redirect:/home";
        } else {
            return "redirect:"+link;
        }
    }

    /*@GetMapping("/home")
    public String goHome(){
        log.info("HomeController 진입");
        return "/member/home";
    }*/

    @GetMapping("/member/memberLogout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model){
        log.info("HomeController 진입");
        // 최신 게시글 5개 가져오기
        List<Board> latestBoard = boardService.findTop5();
        model.addAttribute("latestBoard", latestBoard);

        // DB 전체 개수 세기
        long totalBoard = boardService.countTotal();
        long totalMember = memberService.countTotal();

        model.addAttribute("totalBoard", totalBoard);
        model.addAttribute("totalMember", totalMember);

        return "/member/home";
    }
}

























