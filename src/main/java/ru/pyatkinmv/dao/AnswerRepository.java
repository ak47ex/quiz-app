package ru.pyatkinmv.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pyatkinmv.dao.entities.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
