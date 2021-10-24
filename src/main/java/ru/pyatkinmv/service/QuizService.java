package ru.pyatkinmv.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pyatkinmv.dao.QuizRepository;
import ru.pyatkinmv.dao.entities.Question;
import ru.pyatkinmv.dao.entities.Quiz;
import ru.pyatkinmv.model.QuestionDto;
import ru.pyatkinmv.model.QuizDto;

import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;

    @Transactional
    public Optional<QuizDto> getByShortcut(String shortcut) {
        return quizRepository.findByShortcut(shortcut)
                .map(QuizService::toDto);
    }

    @Transactional
    public Optional<QuizDto> getByCreatorId(Integer creatorId) {
        return quizRepository.findByCreatorId(creatorId)
                .map(QuizService::toDto);
    }

    private static QuizDto toDto(Quiz quiz) {
        return QuizDto.builder()
                .id(quiz.getId())
                .text(quiz.getText())
                .questions(
                        quiz.getQuestions()
                                .stream()
                                .map(QuizService::toDto)
                                .collect(toList())
                )
                .build();
    }

    private static QuestionDto toDto(Question question) {
        return QuestionDto.builder()
                .id(question.getId())
                .text(question.getText())
                .answers(
                        question.getAnswers()
                                .stream()
                                .map(AnswerService::toDto)
                                .collect(toList())
                )
                .correctAnswer(AnswerService.toDto(question.getCorrectAnswer()))
                .build();
    }
}
