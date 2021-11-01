package ru.pyatkinmv.controllers.question;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateQuestionDto {
    @JsonProperty("text")
    private String text;
    @JsonProperty("correct_answer")
    private String correctAnswer;
    @JsonProperty("other_answers")
    private List<String> otherAnswers;
}
