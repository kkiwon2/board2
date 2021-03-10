package org.zerock.board2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.board2.entity.Board;
import org.zerock.board2.entity.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    //특정 게시물의 삭제
    @Modifying
    @Query("delete from Reply r where r.board.bno =:bno")
    void deleteByBno(Long bno);

    //특정 게시물의 번호에 의해서 댓글의 목록을 가져오는 쿼리
    List<Reply> getReplyByBoardOrderByRno(Board board);

}
