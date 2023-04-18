package com.stackoverflow.project.Stackoverflow.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "question_tag")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionTag {
    //    @Id
//    @GeneratedValue(strategy =  GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
    @EmbeddedId
    private QuestionTagKey id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Question.class)
    @MapsId("questionId")
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, targetEntity = Tag.class)
    @MapsId("tagId")
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    public QuestionTag(Question question, Tag tag) {
        this.id = new QuestionTagKey(question.getId(), tag.getId());
        this.question = question;
        this.tag = tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionTag questionTag = (QuestionTag) o;
        return Objects.equals(id, questionTag.id) &&
                Objects.equals(question, questionTag.question) &&
                Objects.equals(tag, questionTag.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question, tag);
    }
}
