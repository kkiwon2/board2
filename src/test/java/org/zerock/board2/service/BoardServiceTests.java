package org.zerock.board2.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board2.dto.BoardDTO;
import org.zerock.board2.dto.PageRequestDTO;
import org.zerock.board2.dto.PageResultDTO;

@SpringBootTest
public class BoardServiceTests {
    @Autowired
    BoardService boardService;

    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for (BoardDTO boardDTO : result.getDtoList()){
            System.out.println(boardDTO);
        }

    }

    @Test
    public void testGet(){

        Long bno = 100L;

        BoardDTO boardDTO = boardService.get(bno);

        System.out.println(boardDTO);
    }

    @Test
    public void testRemove(){

        Long bno = 100L;

        boardService.removeWithReplies(bno);
    }

    @Test
    public void testModify(){

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(2L)
                .title("제목 변경 테스트2")
                .content("내용 변경 테스트2")
                .build();

        boardService.modify(boardDTO);
    }

}
