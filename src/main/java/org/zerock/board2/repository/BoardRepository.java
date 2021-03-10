package org.zerock.board2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board2.entity.Board;
import org.zerock.board2.repository.search.SearchBoardRepository;


import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long>, SearchBoardRepository {

    @Query("select b, w from Board b left join b.member w where b.bno =:bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    //게시물과 댓글 조회
    @Query("select b, r from Board b left join Reply r on r.board = b where b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno")Long bno);

    //게시물, 댓글 수, 회원이메일 조회
    @Query(value = "select b, w, count(r) from Board b left join b.member w left join Reply r on r.board = b " +
            "group by b", countQuery = "select count(b) from Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    //조회화면
    @Query("select b,w, count(r) from Board b left join b.member w left outer join Reply r on r.board = b " +
            "where b.bno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);


}
