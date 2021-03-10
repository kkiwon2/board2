package org.zerock.board2.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "member")
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;       //게시물 번호
    private String title;   //게시물 제목

    private String content; //게시물 내용

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;   //게시물 작성자 Member쪽의 PK를 참조하는 변수로 FK를 의미 DB 테이블의 컬럭명은 member_email로 들어감

    //게시물의 수정을 위해서
    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }
}
