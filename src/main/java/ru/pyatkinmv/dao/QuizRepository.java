package ru.pyatkinmv.dao;

import org.springframework.data.repository.CrudRepository;
import ru.pyatkinmv.dao.entities.Quiz;

import java.util.Optional;

public interface QuizRepository extends CrudRepository<Quiz, Integer> {
    Optional<Quiz> findByShortcut(String shortcut);

    Optional<Quiz> findByCreatorId(Integer creatorId);
}
