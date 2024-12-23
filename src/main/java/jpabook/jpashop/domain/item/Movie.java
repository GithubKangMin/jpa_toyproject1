package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M") // 싱글 테이블 전략에서 사용할 구분값
@Getter
@Setter
public class Movie extends Item {

    private String director;
    private String actor;
}
