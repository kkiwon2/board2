package org.zerock.board2.service;

import org.springframework.stereotype.Service;
import org.zerock.board2.dto.BoardDTO;
import org.zerock.board2.dto.PageRequestDTO;
import org.zerock.board2.dto.PageResultDTO;
import org.zerock.board2.entity.Board;
import org.zerock.board2.entity.Member;


public interface BoardService {

    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    BoardDTO get(Long bno);

    void modify(BoardDTO boardDTO);

    void removeWithReplies(Long bno);

    default Board dtoToEntity(BoardDTO dto){

        Member member = Member.builder().email(dto.getWriterEmail()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .member(member)
                .build();
        return board;
    }

    default BoardDTO entityToDTO(Board board, Member member, Long replyCount){

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())  //long으로 나오므로 int로 변경
                .build();

        return boardDTO;
    }

}
