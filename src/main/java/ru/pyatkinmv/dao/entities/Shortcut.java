package ru.pyatkinmv.dao.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "shortcut")
public class Shortcut {
    @Id
    @GenericGenerator(name = "sequence_quiz_shortcut", strategy = "ru.pyatkinmv.dao.QuizShortcutGenerator")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence_quiz_shortcut")
    private String shortcut;
}
