package ru.pyatkinmv.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.pyatkinmv.dao.entities.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    @Query(value = "SELECT * FROM quiz_user WHERE username = ?1 AND password_hash = ?2 LIMIT 1", nativeQuery = true)
    Optional<User> findByCredentials(String username, String passwordHash);
}
