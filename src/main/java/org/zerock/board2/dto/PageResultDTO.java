package org.zerock.board2.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {

    private List<DTO> dtoList;  //DTO리스트     dtoList = result.stream().map(fn).collect(Collectors.toList());

    private int totalPage; //총 페이지 번호

    private int page; //현재 페이지번호

    private int size;  //목록 사이즈

    private int start,end; //시작 페이지 번호, 끝 페이지 번호

    private boolean prev, next; //이전과 다음

    private List<Integer> pageList; //화면 맨밑에 나타낼 페이지 번호 목록 ex) 1~10페이지 번호 11~20페이지 번호

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn){

        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable){

        this.page = pageable.getPageNumber() + 1;   //현재 페이지번호  0부터 시작하므로 +1
        //this.size = pageable.getPageSize();         //이건 왜필요한거냐
        //System.out.println("getPageSize : " + size);

        //임시 화면의 끝 페이지
        int tempEnd = (int)(Math.ceil(page / 10.0)) * 10; //10페이지씩 나타낸다고 했을때

        start = tempEnd - 9;

        prev = start > 1;

        end = totalPage > tempEnd ? tempEnd: totalPage;

        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
        //boxed()스트림 중간연산을 이용해서 int타입을 Integer저로 변환해야됨 -> 왜냐 List컬렉션은 기본변수int를 제네릭처리 못하니까
    }
}
