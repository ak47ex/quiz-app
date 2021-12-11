package ru.pyatkinmv.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.pyatkinmv.dao.UserRepository;
import ru.pyatkinmv.dao.UserTokenRepository;
import ru.pyatkinmv.dao.entities.User;
import ru.pyatkinmv.dao.entities.UserToken;

import java.lang.Exception;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Stream;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;

    @Transactional
    public Optional<User> getByToken(String token) {
        return userTokenRepository.findById(token).flatMap((ut) -> {
            if (ut.getUserId() != null && ut.getUserId() != 0) {
                return userRepository.findById(ut.getUserId());
            } else {
                return Optional.empty();
            }
        });
    }

    @Transactional
    public Optional<User> getById(Integer userId) {
        return userRepository.findById(userId);
    }

    @Transactional
    public Optional<User> createNewUser(String token) {
        if (userTokenRepository.findById(token).isPresent()) {
            return Optional.empty();
        }
        User user = User.builder().build();
        try {
            User createdUser = userRepository.save(user);
            UserToken userToken = UserToken.builder().userId(createdUser.getId()).token(token).build();
            userTokenRepository.save(userToken);
            return Optional.of(createdUser);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<User> register(String username, String password, String token) {
        checkNotNull(username, "username is null");
        checkNotNull(password, "password is null");

        Integer userId = null;
        if (token != null) {
            Optional<UserToken> ut = userTokenRepository.findById(token);
            if (ut.isPresent()) {
                userId = ut.get().getUserId();
            }
        }

        if (userRepository.findByUsername(username).isPresent()) {
            return Optional.empty();
        }
        User user = User.builder().id(userId).username(username).passwordHash(encryptPassword(password)).build();
        User savedUser = userRepository.save(user);
        if (token != null) {
            userTokenRepository.save(UserToken.builder().userId(savedUser.getId()).token(token).build());
        }
        return Optional.of(savedUser);
    }

    public Optional<String> authorize(String username, String password) {
        checkNotNull(username, "username is null");
        checkNotNull(password, "password is null");

        Optional<User> user = userRepository.findByCredentials(username, encryptPassword(password));
        if (user.isPresent()) {
            byte[] randomBytes = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
            byte[] base64Bytes = Base64.getEncoder().encode(randomBytes);
            String token = new String(base64Bytes);
            userTokenRepository.save(UserToken.builder().token(token).userId(user.get().getId()).build());
            return Optional.of(token);
        }
        return Optional.empty();
    }

    private void checkNotNull(Object obj) {
        checkNotNull(obj, "null argument");
    }

    private void checkNotNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    private static final String SALT = "/P?1p$*as[,q(.&3Mdsi-q==H7%";

    private static String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(SALT.getBytes(StandardCharsets.UTF_8));
            byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return new String(hashedPassword);
        } catch (NoSuchAlgorithmException nsae) {
            return null;
        }
    }
}