package ru.pyatkinmv.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pyatkinmv.dao.entities.Question;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizDto {
    private Integer id;
    private String text;
    private List<QuestionDto> questions;
}
