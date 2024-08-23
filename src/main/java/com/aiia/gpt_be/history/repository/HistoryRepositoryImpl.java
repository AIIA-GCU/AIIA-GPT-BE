package com.aiia.gpt_be.history.repository;

import com.aiia.gpt_be.question.QQuestionHistory;
import com.aiia.gpt_be.question.QuestionHistory;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

@RequiredArgsConstructor
public class HistoryRepositoryImpl implements HistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<QuestionHistory> getAllHistories(Pageable pageable) {
        QQuestionHistory q = new QQuestionHistory("q");

        List<QuestionHistory> content = queryFactory.selectFrom(q)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(q.count())
                .from(q);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
