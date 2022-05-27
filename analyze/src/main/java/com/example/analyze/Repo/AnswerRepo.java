package com.example.analyze.Repo;

import com.example.analyze.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepo extends JpaRepository<Answer, Integer> {
    List<Answer> findByQuestionIdOrderByCreateDateDesc(int questionId);
    List<Answer> findByQuestionIdOrderByVoteDesc(int questionId);
    Answer findFirstByQuestionIdOrderByVote(int questionId);

    @Query(value = "SELECT * FROM answer JOIN\n" +
            "    (SELECT\n" +
            "           a.id,\n" +
            "           t.match_points*10 + a.rank_point + q2.rank_point rank_points,\n" +
            "           RANK() OVER (PARTITION BY a.question_id\n" +
            "               ORDER BY t.match_points*15 + a.rank_point + q2.rank_point DESC) a_rank\n" +
            "    FROM answer a\n" +
            "        JOIN\n" +
            "            (SELECT\n" +
            "                q.id question_id,\n" +
            "                a.id answer_id,\n" +
            "                substrCount(lower(q.name), ?1) * 2 +\n" +
            "                substrCount(lower(a.content), ?1) match_points\n" +
            "            FROM question q\n" +
            "                JOIN answer a on q.id = a.question_id\n" +
            "            ORDER BY match_points DESC) t ON a.id = t.answer_id\n" +
            "    JOIN question q2 on a.question_id = q2.id\n" +
            "    WHERE t.match_points*10 + a.rank_point + q2.rank_point  > 0\n" +
            "    ORDER BY rank_points DESC) as tt\n" +
            "USING (id)\n" +
            "WHERE a_rank = 1",
            nativeQuery = true)
    List<Answer> findAnswer(String reg);
}
