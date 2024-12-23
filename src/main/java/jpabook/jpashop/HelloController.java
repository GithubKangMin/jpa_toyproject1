package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello") // localhost:8080/hello로 들어오면 이 메소드가 호출된다.
    public String hello(Model model) { // model은 서버 템플릿 엔진에서 사용할 수 있는 객체를 담아서 전달할 수 있다.
        model.addAttribute("data", "hello!!!"); // model에 data라는 이름으로 hello!!!를 넣어준다.
        return "hello"; // resources/templates/hello.html로 가라 자동으로 html 이 붙는게 관례
    }
}
