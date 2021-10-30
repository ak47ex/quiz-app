package ru.pyatkinmv.dao;

import org.springframework.data.repository.CrudRepository;
import ru.pyatkinmv.dao.entities.Answer;
import ru.pyatkinmv.dao.entities.Secret;

public interface SecretRepository extends CrudRepository<Secret, Integer> {
}
