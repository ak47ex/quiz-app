package ru.pyatkinmv.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.pyatkinmv.dao.entities.User;
import ru.pyatkinmv.dao.entities.UserToken;

import java.util.List;

public interface UserTokenRepository extends CrudRepository<UserToken, String> {
    @Query(value = "SELECT * FROM user_token WHERE user_id = ?1", nativeQuery = true)
    List<User> findByUserId(Integer userId);
}
