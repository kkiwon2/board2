package org.zerock.board2.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")
public class Reply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;       //댓글 번호

    private String text;    //댓글 내용

    private String replyer; //댓글 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;    //Board쪽의 PK를 참조 DB테이블의 컬럼명은 board_bno로 들어감

}
