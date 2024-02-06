package minu.subran.repository;

import minu.subran.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByName(String name);
    Optional<Member> findByEmail(String memberEmail);
    boolean existsByEmail(String memberEmail);
    boolean existsByName(String memberName);
}