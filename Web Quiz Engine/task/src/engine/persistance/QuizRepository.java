package engine.persistance;

import engine.businesslayer.Quiz;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long> {
    Quiz findQuizById(Long id);
    boolean existsQuizById(Long id);
}
