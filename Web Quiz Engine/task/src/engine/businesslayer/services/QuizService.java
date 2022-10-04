package engine.businesslayer.services;

import engine.businesslayer.Quiz;
import engine.persistance.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public boolean existsQuizById(Long id) {
        return quizRepository.existsQuizById(id);
    }

    public Quiz findQuizById(Long id) {
        return quizRepository.findQuizById(id);
    }

    public Page<Quiz> getAllQuizzes(Integer page, Integer pageSize) {
        Pageable paging = PageRequest.of(page, pageSize);

        return quizRepository.findAll(paging);
    }

    public Quiz save(Quiz toSave) {
        return quizRepository.save(toSave);
    }

    public void deleteById(Long id) {
        quizRepository.deleteById(id);
    }
}
