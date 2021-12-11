package ru.pyatkinmv.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.pyatkinmv.dao.entities.Quiz;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends CrudRepository<Quiz, Integer> {
    Optional<Quiz> findByShortcut(String shortcut);

    @Query(value = "SELECT * FROM quiz WHERE creator = ?1", nativeQuery = true)
    List<Quiz> findByCreator(Integer creator);
}
