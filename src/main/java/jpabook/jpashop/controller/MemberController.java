package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) { // @Valid: validation을 사용하겠다는 어노테이션

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/"; // 회원가입이 끝나면 홈화면으로 리다이렉트(원래대로 돌아감)
    }

    @GetMapping("/members") //엔티티는 api로 외부로 노출해선 안되지만 템플릿엔진에서는 상관없다.
    public String list(Model model) { // 회원 목록 조회
        List<Member> members = memberService.findMembers(); // 회원 목록을 조회해서
        model.addAttribute("members", members); // 모델에 담아서
        return "members/memberList"; // 회원 목록 화면으로 이동 (템플릿 엔진)
    }

}
