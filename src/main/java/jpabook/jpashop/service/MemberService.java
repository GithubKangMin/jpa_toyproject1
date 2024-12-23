package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //스프링 빈으로 등록
@Transactional(readOnly = true) //변경
@RequiredArgsConstructor    //final이 있는 필드만 가지고 생성자를 자동으로 만들어준다.(굳이 코드 안서도 됨)
public class MemberService {

    private final MemberRepository memberRepository;



    /**
     * 회원 가입
     */
    @Transactional  //변경
    public Long join(Member member) {

        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);  //회원 저장
        return member.getId();  //회원 저장 후 아이디 반환 꺼내면 항상 값이 있어야 한다. 값이 없으면 에러가 발생한다.
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());   //중복 회원 검증
        if (!findMembers.isEmpty()) {   //중복 회원 검증 이름으로 회원을 찾아서 이미 존재하면 에러를 발생시킨다.
            throw new IllegalStateException("이미 존재하는 회원입니다.");  //이미 존재하는 회원이면 에러 발생
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }


    //단건 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
