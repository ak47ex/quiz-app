package ru.pyatkinmv.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pyatkinmv.dao.entities.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
