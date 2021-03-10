package org.zerock.board2.service;

import org.zerock.board2.dto.ReplyDTO;
import org.zerock.board2.entity.Board;
import org.zerock.board2.entity.Reply;

import java.util.List;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);

    List<ReplyDTO> getList(Long bno);

    void modify(ReplyDTO replyDTO);

    void remove(Long rno);

    //ReplyDTO를 Reply객체로 변환 Board객체의 처리가 수반됨
    default Reply dtoToEntity(ReplyDTO replyDTO){

        Board board = Board.builder().bno(replyDTO.getBno()).build();

        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .board(board)
                .build();
        return reply;
    }

    //Reply객체를 ReplyDTO로 변환 Board 객체가 필요하지 않고 Board의 번호필요함
    default ReplyDTO entityToDTO(Reply reply){
        ReplyDTO dto = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                //.bno(reply.getBoard().getBno())
                .build();
        return dto;
    }
}
