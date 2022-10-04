package engine.persistance;

import org.springframework.data.repository.CrudRepository;
import engine.businesslayer.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findUserByEmail(String email);
}
