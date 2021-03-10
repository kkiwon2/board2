package org.zerock.board2.repository;

import jdk.nashorn.internal.runtime.options.Option;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board2.entity.Board;
import org.zerock.board2.entity.Reply;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired
    ReplyRepository replyRepository;

    @Test
    public void insertReply(){

        IntStream.rangeClosed(1,300).forEach(i->{
            long bno = (long)(Math.random() * 100) +1;

            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .text("Reply......" + i)
                    .board(board)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);
        });
    }//insert

    @Test
    public void readReply1(){
        Optional<Reply> result = replyRepository.findById(1L);

        Reply reply = result.get();
        System.out.println(reply);
        System.out.println(reply.getBoard());
    }

    @Test
    public void testListByBoard(){
        List<Reply> replyList = replyRepository.getReplyByBoardOrderByRno(Board.builder().bno(97L).build());
        replyList.forEach(reply -> System.out.println(reply));
    }
}
