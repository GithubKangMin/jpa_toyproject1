package jpabook.jpashop.repository;

import static org.junit.Assert.*;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class) // JUnit에 내장된 실행자 외에 다른 실행자를 실행시킨다.
@SpringBootTest // 스프링 컨테이너와 테스트를 함께 실행
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository; // 테스트 대상

    @Test // 테스트 코드
    @Transactional // 테스트 케이스에 있으면 테스트 케이스가 끝나면 롤백을 해준다.
    public void testMember() throws Exception {
        // given
        Member member = new Member(); // member 객체 생성
        member.setName("memberA"); // member의 name을 설정

        // when
        Long saveId = memberRepository.save(member); // member를 저장하고 저장된 id를 반환한다.
        Member findMember = memberRepository.findOne(saveId); // 저장된 id로 member를 찾는다.
        // then
        Assertions.assertThat (findMember.getId()). isEqualTo(member.getId()); // findMember와 member의 id가 같은지 확인
        Assertions.assertThat (findMember.getName()). isEqualTo(member.getName ()); // findMember와 member의 name이 같은지 확인

    }

}