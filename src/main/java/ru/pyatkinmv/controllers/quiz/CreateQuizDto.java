package ru.pyatkinmv.controllers.quiz;

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
public class CreateQuizDto {
    @JsonProperty("secret")
    private String secret;
    @JsonProperty("questions_ids")
    private List<Integer> questionsIds;
}
