package com.sideteam.groupsaver.domain.board.repository;

import com.sideteam.groupsaver.domain.board.domain.Board;
import com.sideteam.groupsaver.domain.board.domain.QBoard;
import com.sideteam.groupsaver.domain.board.dto.BoardResponseDto;
import com.sideteam.groupsaver.global.config.QueryDSLConfig;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private Logger logger = LoggerFactory.getLogger(getClass());

    final EntityManager em;

    final QueryDSLConfig query;

    QBoard qBoard = new QBoard("tn");

//    private BooleanBuilder whereList(SearchDTO searchDTO) throws Exception {
//
//        BooleanBuilder builder = new BooleanBuilder();
//
//        if (searchDTO.getSearchOption() != null && !searchDTO.getSearchOption().equals("")
//                && searchDTO.getSearchText() != null && !searchDTO.getSearchText().equals("")) {
//
//            switch (searchDTO.getSearchOption()) {
//                case "제목":
//                    builder.and(qBoard.title.contains(searchDTO.getSearchText()));
//                    break;
//                case "내용":
//                    builder.and(qBoard.content.contains(searchDTO.getSearchText()));
//                    break;
//                case "작성자":
//                    builder.and(qBoard.regId.contains(searchDTO.getSearchText()));
//                    break;
//            }
//        }
//
//        // 메인공지가 아닌 공지사항만 출력하기 위한 조건
//        builder.and(qNotice.mainYn.eq("N"));
//
//        return builder;
//    }

    public List<Board> noticeList() throws Exception {

        List<Board> noticeList = query.factory()
                .select(qBoard)
                .from(qBoard)
                .fetch();

        return noticeList;
    }

    public BoardResponseDto read(long seq) throws Exception {
        Board entity = em.find(Board.class, seq);
        return BoardResponseDto.builder().build();
    }

    @Transactional
    public BoardResponseDto create(Board entity) throws Exception {
        em.persist(entity);
        return BoardResponseDto.builder().build();
    }

    @Transactional
    public BoardResponseDto update(Board entity) throws Exception {
        em.merge(entity);
        return BoardResponseDto.builder().build();
    }

    @Transactional
    public boolean delete(long seq) throws Exception {
        em.remove(em.find(Board.class, seq));
        return true;
    }

}

