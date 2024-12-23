package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // JPA가 관리하는 Entity
@Getter @Setter // @Getter, @Setter를 사용하면 getter, setter를 자동으로 생성해준다. 롬복 라이브러리를 통해 간결해진 코드
public class Member {

    @Id @GeneratedValue // @Id: PK, @GeneratedValue: 자동 생성
    @Column(name = "member_id") // 컬럼명을 member_id로 매핑
    private Long id; // 식별자

    private String name; // 게터와 세터 어노테이션은 필드명을 기준으로 롬복 라이브러리가 세터와 세터 메서드를 생성해준다

    @Embedded // 내장 타입
    private Address address; // 주소

    @JsonIgnore // 연관관계 주의
    @OneToMany(mappedBy = "member")// Order 테이블의 member 필드에 의해 매핑된 것이다.
    private List<Order> orders = new ArrayList<>(); // 주문 목록

}