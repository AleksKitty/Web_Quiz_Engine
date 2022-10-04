package engine.persistance;

import engine.businesslayer.CompletedQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CompletedQuizRepository extends JpaRepository<CompletedQuiz, Long> {

    @Query(value = "SELECT c FROM CompletedQuiz c WHERE c.email = :email")
    Page<CompletedQuiz> findAllQuizzesWithPagination(Pageable pageable, @Param("email") String email);
}
