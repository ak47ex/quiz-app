package ru.pyatkinmv.dao;

import org.springframework.data.repository.CrudRepository;
import ru.pyatkinmv.dao.entities.Answer;

public interface AnswerRepository extends CrudRepository<Answer, Integer> {
}
