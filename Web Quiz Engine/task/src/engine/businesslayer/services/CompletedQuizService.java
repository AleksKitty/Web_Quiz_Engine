package engine.businesslayer.services;

import engine.businesslayer.CompletedQuiz;
import engine.persistance.CompletedQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
public class CompletedQuizService {

    EntityManager entityManager;

    private final CompletedQuizRepository completedQuizRepository;

    @Autowired
    public CompletedQuizService(CompletedQuizRepository completedQuizRepository, EntityManager entityManager) {
        this.completedQuizRepository = completedQuizRepository;
        this.entityManager = entityManager;
    }

    public Page<CompletedQuiz> getAllCompletedQuizzes(Integer page, Integer pageSize, String email) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by("completedAt").descending());

        return completedQuizRepository.findAllQuizzesWithPagination(paging, email);
    }

    public void save(CompletedQuiz toSave) {
        completedQuizRepository.save(toSave);
    }

    @Transactional
    public void saveCompletedQuiz(CompletedQuiz completedQuiz){
        entityManager.persist(completedQuiz);
    }
}
