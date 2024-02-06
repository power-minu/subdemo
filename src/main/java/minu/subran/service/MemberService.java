package minu.subran.service;

import lombok.RequiredArgsConstructor;
import minu.subran.domain.Member;
import minu.subran.dto.JoinRequest;
import minu.subran.dto.LoginRequest;
import minu.subran.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean checkEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean checkMemberNameDuplicate(String memberName) {
        return memberRepository.existsByName(memberName);
    }

    @Transactional
    public void join(JoinRequest req) {
        memberRepository.save(req.toEntity());
    }

    public Member login(LoginRequest req) {
        Optional<Member> optionalMember = memberRepository.findByEmail(req.getLoginEmail());
        //찾았는데 없을 수도 있으니까 Optional

        // loginId와 일치하는 User가 없으면 null return
        if(optionalMember.isEmpty()) {
            return null;
        }

        Member member = optionalMember.get();

        // 찾아온 User의 password와 입력된 password가 다르면 null return
        if(!member.getPassword().equals(req.getPassword())) {
            return null;
        }

        return member;
    }

    public Member getLoginMemberById(Long memberId) {
        if(memberId == null) return null;

        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if(optionalMember.isEmpty()) return null;

        return optionalMember.get();
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 id로 조회
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
