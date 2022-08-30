package nova.novahomepage.repository.dsl;

import com.querydsl.core.QueryFactory;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import nova.novahomepage.controller.dto.BoardDto;
import nova.novahomepage.controller.dto.BoardPageDto;
import nova.novahomepage.domain.entity.Board;
import nova.novahomepage.domain.entity.QBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static nova.novahomepage.domain.entity.QBoard.*;

@Repository
@RequiredArgsConstructor
public class BoardQueryDsl {

    private final JPAQueryFactory queryFactory;

    public Page<BoardPageDto> findAllBoardByType(String typeName, Pageable pageable) {
        List<Board> boardList = queryFactory.select(board)
                .from(board)
                .where(board.typeName.eq(typeName))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        List<BoardPageDto> boardPageDtos = new ArrayList<>();
        for (Board board : boardList) {
            boardPageDtos.add(new BoardPageDto(board.getId(), board.getTypeName(), board.getTitle(), board.getGood(),
                    board.getUsers().getName(), board.getUsers().getStudentNumber()));
        }

        return PageableExecutionUtils.getPage(boardPageDtos, pageable, () -> getCount(typeName));
    }

    private Long getCount(String typeName) {
        return queryFactory.selectFrom(board)
                .where(board.typeName.eq(typeName))
                .fetchCount();
    }
}
