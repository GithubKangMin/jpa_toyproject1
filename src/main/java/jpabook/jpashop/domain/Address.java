package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable // 값 타입- JPA 내장 타입(어딘가에 내장이 될 수 있다)
@Getter // 값 타입은 변경 불가능하게 설계해야 한다. -> 세터 없이
public class Address {
    // 값 타입은 불변 객체로 만들어야 한다. -> 생성자로만 값을 설정할 수 있게 한다.
    private String city;
    private String street;
    private String zipcode;


    protected Address() { // JPA 스펙상 만들어둔 기본 생성자 생성할때만 값이 생성이 되고 그 이후에는 값을 변경할 수 없게 만들어둔다. 기본생성자가 있어야 reflection이나 proxy같은 기술이 동작할 수 있다.
    }

    public Address(String city, String street, String zipcode) { // 생성자
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
