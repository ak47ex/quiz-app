package ru.pyatkinmv.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pyatkinmv.dao.AnswerRepository;
import ru.pyatkinmv.dao.SecretRepository;
import ru.pyatkinmv.dao.entities.Answer;
import ru.pyatkinmv.dao.entities.Secret;
import ru.pyatkinmv.model.AnswerDto;
import ru.pyatkinmv.model.SecretDto;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class SecretService {
    private final SecretRepository secretRepository;

    static SecretDto toDto(Secret secret) {
        return new SecretDto(secret.getId(), secret.getText());
    }

    private static Secret toEntity(SecretDto secretDto) {
        return Secret.builder()
                .text(secretDto.getText())
                .build();
    }

    @Transactional
    public Optional<SecretDto> getById(Integer id) {
        return secretRepository.findById(id)
                .map(SecretService::toDto);
    }

    @Transactional
    public SecretDto create(SecretDto secretDto) {
        return toDto(
                secretRepository.save(
                        toEntity(secretDto)
                )
        );
    }

    @Transactional
    public boolean deleteById(Integer id) {
        try {
            secretRepository.deleteById(id);

            return true;
        } catch (EmptyResultDataAccessException ex) {
            return false;
        }
    }
}
