package engine.businesslayer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "quizzes")
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @NotBlank
    @Column(name = "title")
    String title;
    @NotBlank
    @Column(name = "text")
    String text;
    @NotNull
    @Size(min = 2)
    @ElementCollection
    List<String> options; // text options for the quiz
    @ElementCollection
    List<Integer> answer;
    @Column(name = "email")
    String email;

    public Quiz() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @JsonIgnore
    public List<Integer> getAnswer() {
        return answer;
    }

    @JsonProperty
    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    @JsonIgnore
    public String getEmail() {
        return email;
    }

    @JsonProperty
    public void setEmail(String email) {
        this.email = email;
    }
}
