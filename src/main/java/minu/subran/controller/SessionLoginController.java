package minu.subran.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import minu.subran.domain.Member;
import minu.subran.dto.JoinRequest;
import minu.subran.dto.LoginRequest;
import minu.subran.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class SessionLoginController {

    private final MemberService memberService;

    @GetMapping(value = {"", "/"})
    public String home(Model model, @SessionAttribute(name = "memberId", required = false) Long memberId) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        Member loginMember = memberService.getLoginMemberById(memberId);

        if (loginMember != null) {
            model.addAttribute("memberName", loginMember.getName());
        }

        return "home";
    }

    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        model.addAttribute("joinRequest", new JoinRequest());
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute JoinRequest joinRequest, BindingResult bindingResult, Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        if (memberService.checkEmailDuplicate(joinRequest.getMemberEmail())) {
            bindingResult.addError(new FieldError("joinRequest", "memberEmail", "이메일이 중복됩니다."));
        }

        if (memberService.checkMemberNameDuplicate(joinRequest.getMemberName())) {
            bindingResult.addError(new FieldError("joinRequest", "memberName", "닉네임이 중복됩니다."));
        }

        if (!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
            bindingResult.addError(new FieldError("joinRequest", "passwordCheck", "비밀번호를 다시 확인해주십시오."));
        }

        if (bindingResult.hasErrors()) {
            return "join";
        }

        memberService.join(joinRequest);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest, BindingResult bindingResult,
                        HttpServletRequest httpServletRequest, Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        Member member = memberService.login(loginRequest);

        //로그인 실패
        if (member == null) {
            bindingResult.reject("loginFail", "로그인 정보가 잘못되었습니다.");
        }
        if (bindingResult.hasErrors()) {
            return "login";
        }

        //로그인 성공. 세션 생성

        //세션 생성 전 기존 세션을 파기
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true); //세션이 없는 상태니까 생성.
        session.setAttribute("memberId", member.getId());
        session.setMaxInactiveInterval(1800); //세션은 30분동안 유지됨.

        return "redirect:/users";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        HttpSession session = request.getSession(false); //세션 없으면 null 리턴.
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/users";
    }
}
