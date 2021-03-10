package org.zerock.board2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.board2.dto.BoardDTO;
import org.zerock.board2.dto.PageRequestDTO;
import org.zerock.board2.dto.PageResultDTO;
import org.zerock.board2.entity.Board;
import org.zerock.board2.entity.Member;
import org.zerock.board2.repository.BoardRepository;
import org.zerock.board2.repository.ReplyRepository;

import javax.transaction.Transactional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto) {

        log.info(dto);

        Board board = dtoToEntity(dto);

        boardRepository.save(board);

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        log.info(pageRequestDTO);

        Function<Object[], BoardDTO> fn = obj ->
            entityToDTO((Board)obj[0], (Member) obj[1], (Long) obj[2]);

//        Page<Object[]> result =
//                boardRepository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));
        Page<Object[]> result = boardRepository.searchPage(pageRequestDTO.getType(), pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending()));

        return new PageResultDTO<>(result, fn);

    }

    @Override
    public BoardDTO get(Long bno) {

        Object result = boardRepository.getBoardByBno(bno);
        Object[] arr = (Object[])result;
        return entityToDTO((Board)arr[0], (Member)arr[1], (Long)arr[2]);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {

        //댓글 부터 삭제해야됨
        replyRepository.deleteByBno(bno);
        //게시글 삭제
        boardRepository.deleteById(bno);
    }

    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {

        Board board = boardRepository.getOne(boardDTO.getBno());

        board.changeTitle(boardDTO.getTitle());

        board.changeContent(boardDTO.getContent());

        //save가 없어도 board엔티티는 연속성컨텍스트에 있는 객체라서 JPA특성으로 인해 자동변경되지만 습관적으로 save를 날려야됨
        //boardRepository.save(board);
    }
}
