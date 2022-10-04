package engine.presentation;

import engine.businesslayer.CompletedQuiz;
import engine.businesslayer.services.CompletedQuizService;
import engine.presentation.SupportClasses.AnswerInput;
import engine.presentation.SupportClasses.Response;
import engine.businesslayer.Quiz;
import engine.businesslayer.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class QuizController {
    @Autowired
    QuizService quizService;
    @Autowired
    CompletedQuizService completedQuizService;
    private final Response responseCorrect = new Response(true, "Congratulations, you're right!");
    private final Response responseWrong = new Response(false, "Wrong answer! Please, try again.");

    @GetMapping("/api/quizzes/{id}")
    public Quiz getQuizById(@PathVariable long id) {
        if (!quizService.existsQuizById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return quizService.findQuizById(id);
    }

    // get All quizzes
    @GetMapping("/api/quizzes")
    public ResponseEntity<Page<Quiz>> getAllQuizzes(@RequestParam(defaultValue = "0") Integer page,
                                                    @RequestParam(defaultValue = "10") Integer pageSize) {

        Page<Quiz> pageResult = quizService.getAllQuizzes(page, pageSize);

        return new ResponseEntity<>(pageResult, new HttpHeaders(), HttpStatus.OK);
    }

    // get all completions of quizzes for a specified user
    @GetMapping("/api/quizzes/completed")
    public ResponseEntity<Page<CompletedQuiz>> getAllCompetedQuizzes(@AuthenticationPrincipal UserDetails details,
                                                                     @RequestParam(defaultValue = "0") Integer page,
                                                                     @RequestParam(defaultValue = "10") Integer pageSize) {

        Page<CompletedQuiz> pageResult = completedQuizService.getAllCompletedQuizzes(page, pageSize, details.getUsername());

        return new ResponseEntity<>(pageResult, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/api/quizzes")
    public Quiz addQuiz(@Valid @RequestBody Quiz quiz, @AuthenticationPrincipal UserDetails details) {
        if (quiz.getAnswer() == null) {
            quiz.setAnswer(new ArrayList<>());
        }
        // sort for future comparisons
        Collections.sort(quiz.getAnswer());

        // store who created id
        quiz.setEmail(details.getUsername());

        return quizService.save(quiz);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Response postAddress(@AuthenticationPrincipal UserDetails details,
                                @PathVariable long id,
                                @RequestBody AnswerInput answer) {
        if (!quizService.existsQuizById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (!deepEquals(quizService.findQuizById(id).getAnswer(), answer.getAnswer())) {
            return responseWrong;
        }

        // save to db
        CompletedQuiz completedQuiz = new CompletedQuiz();
        completedQuiz.setQuizID(id);
        completedQuiz.setEmail(details.getUsername());
        completedQuiz.setCompletedAt(Timestamp.from(java.time.Clock.systemUTC().instant()));
        completedQuizService.saveCompletedQuiz(completedQuiz); // to generate id

        completedQuizService.save(completedQuiz);

        return responseCorrect;
    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable long id, @AuthenticationPrincipal UserDetails details) {
        if (quizService.findQuizById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (!details.getUsername().equals(quizService.findQuizById(id).getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        quizService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public boolean deepEquals(List<Integer> byId, List<Integer> fromAnswer) {
        if (byId.size() != fromAnswer.size())
            return false;

        for (int i = 0; i < byId.size(); i++) {
            if (!byId.get(i).equals(fromAnswer.get(i))) {
                return false;
            }
        }

        return true;
    }
}
