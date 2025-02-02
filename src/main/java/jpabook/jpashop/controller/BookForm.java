package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm { //상품등록

    //상품의 공통속성
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;


    //책의 고유 속성
    private String author;
    private String isbn;
}
