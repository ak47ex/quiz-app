package ru.pyatkinmv.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question_answer")
public class QuestionAnswer implements Serializable {

    @EmbeddedId
    private QuestionAnswerId id;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class QuestionAnswerId implements Serializable {
        @OneToOne(fetch = FetchType.EAGER, optional = false)
        @JoinColumn(name = "question_id")
        private Question question;

        @OneToOne(fetch = FetchType.EAGER, optional = false)
        @JoinColumn(name = "answer_id")
        private Answer answer;
    }

}
