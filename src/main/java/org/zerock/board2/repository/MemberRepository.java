package org.zerock.board2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.board2.entity.Member;

public interface MemberRepository extends JpaRepository<Member,String> {
}
