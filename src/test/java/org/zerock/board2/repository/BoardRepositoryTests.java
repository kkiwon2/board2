package org.zerock.board2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.board2.dto.BoardDTO;
import org.zerock.board2.entity.Board;
import org.zerock.board2.entity.Member;
import org.zerock.board2.service.BoardService;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardService boardService;

    @Test
    public void insertBoard(){

        IntStream.rangeClosed(1,100).forEach(i->{
            Member member = Member.builder().email("user"+i+"@aaa.com").build();

            Board board = Board.builder()
                    .title("Title....." +i)
                    .content("Content...." +i)
                    .member(member)
                    .build();

            boardRepository.save(board);
        });
    }//insert

    @Transactional
    @Test
    public void testRead1(){

        Optional<Board> result = boardRepository.findById(100L); //데이터베이스에 존재하는 번호
        Board board = result.get();
        System.out.println(board);
        System.out.println(board.getMember());
    }

    @Test
    public void testReadWithWriter(){

        Object result = boardRepository.getBoardWithWriter(100L);
        Object[] arr = (Object[])result;
        System.out.println("--------------------------");
        System.out.println(arr[0]);
    }

    @Test
    public void testGetBoardWithReply(){
        List<Object[]> result = boardRepository.getBoardWithReply(100L);
        for(Object[] arr : result){
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testWithReplyCount(){

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
        result.stream().forEach(objects -> {
            Object[] arr = (Object[]) objects;
            System.out.println(arr[0]);
            //System.out.println(Arrays.toString(arr));
        });

    }

    @Test
    public void testRead3(){
        Object result = boardRepository.getBoardByBno(100L);

        Object[] arr = (Object[]) result;

        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void tetRegister(){

        BoardDTO dto = BoardDTO.builder()
                .title("Test")
                .content("Test.")
                .writerEmail("user55@aaa.com")
                .build();

        Long bno = boardService.register(dto);
    }


    @Test
    public void testSearch1(){
        boardRepository.search1();
    }

    @Test
    public void testSearchPage(){

        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.searchPage("tcw","1", pageable);
    }
}
