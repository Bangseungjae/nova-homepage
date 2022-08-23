package nova.novahomepage.repository;

import com.querydsl.core.QueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import nova.novahomepage.domain.entity.QUsers;
import nova.novahomepage.domain.entity.Users;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UsersQueryDsl {
    private final JPAQueryFactory queryFactory;

    public Users findByStudentNumber(String studentNumber) {
        QUsers m = new QUsers("m");
        return queryFactory.selectFrom(m)
                .where(m.studentNumber.eq(studentNumber))
                .fetchOne();
    }
}
