package ru.pyatkinmv.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.pyatkinmv.dao.entities.QuestionAnswer;

import java.util.List;
import java.util.Optional;

public interface QuestionAnswerRepository extends CrudRepository<QuestionAnswer, QuestionAnswer.QuestionAnswerId> {
    @Query(value = "SELECT * FROM question_answer WHERE question_id = ?1 LIMIT 1", nativeQuery = true)
    Optional<QuestionAnswer> findByQuestionId(Integer questionId);

    @Query(value = "SELECT * FROM question_answer WHERE question_id IN (?1)", nativeQuery = true)
    List<QuestionAnswer> findAllByQuestionIds(Iterable<Integer> questionIds);
}
