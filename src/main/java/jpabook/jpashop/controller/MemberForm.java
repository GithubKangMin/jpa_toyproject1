package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수 입니다") // validation: 값이 비어있으면 안된다. 값이 비어있으면 에러를 발생시킨다. -> validation을 통과하지 못하면 다시 입력하게 한다.
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
